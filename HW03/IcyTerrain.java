import java.util.*;

public class IcyTerrain {
    private ITerrainObject[][] grid;
    private List<Penguin> penguins;
    private List<Hazard> hazards;
    private List<Food> foods;
    private Random random;
    private final int SIZE = 10;

    public IcyTerrain() {
        grid = new ITerrainObject[SIZE][SIZE];
        penguins = new ArrayList<>();
        hazards = new ArrayList<>();
        foods = new ArrayList<>();
        random = new Random();
        initializeTerrain();
    }

    private void initializeTerrain() {
        // 1. Penguenler
        List<String> pTypes = Arrays.asList("King", "Emperor", "Royal", "Rockhopper");
        for (int i = 1; i <= 3; i++) {
            String type = pTypes.get(random.nextInt(pTypes.size()));
            int r, c;
            do {
                if (random.nextBoolean()) {
                    r = random.nextBoolean() ? 0 : SIZE - 1;
                    c = random.nextInt(SIZE);
                } else {
                    r = random.nextInt(SIZE);
                    c = random.nextBoolean() ? 0 : SIZE - 1;
                }
            } while (grid[r][c] != null);

            Penguin p = null;
            switch (type) {
                case "King": p = new KingPenguin(i, r, c); break;
                case "Emperor": p = new EmperorPenguin(i, r, c); break;
                case "Royal": p = new RoyalPenguin(i, r, c); break;
                case "Rockhopper": p = new RockhopperPenguin(i, r, c); break;
            }
            penguins.add(p);
            grid[r][c] = p;
        }

        // 2. Hazardlar
        for (int i = 0; i < 15; i++) placeHazard();

        // 3. Yemekler
        String[] fNames = {"Krill", "Crustacean", "Anchovy", "Squid", "Mackerel"};
        for (int i = 0; i < 20; i++) {
            int r, c;
            do { r = random.nextInt(SIZE); c = random.nextInt(SIZE); } 
            while (grid[r][c] instanceof Hazard || grid[r][c] instanceof Penguin);
            
            if (!(grid[r][c] instanceof Hazard) && !(grid[r][c] instanceof Penguin)) {
                Food f = new Food(r, c, fNames[random.nextInt(5)], random.nextInt(5) + 1);
                foods.add(f);
                if (grid[r][c] == null) grid[r][c] = f;
            } else { i--; }
        }
    }

    private void placeHazard() {
        int r, c;
        do { r = random.nextInt(SIZE); c = random.nextInt(SIZE); } while (grid[r][c] != null);
        
        int type = random.nextInt(4);
        Hazard h = null;
        switch (type) {
            case 0: h = new LightIceBlock(r, c); break;
            case 1: h = new HeavyIceBlock(r, c); break;
            case 2: h = new SeaLion(r, c); break;
            case 3: h = new HoleInIce(r, c); break;
        }
        hazards.add(h);
        grid[r][c] = h;
    }

    public void printGrid() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE; j++) {
                String content = "    ";
                if (grid[i][j] != null) content = String.format(" %-2s ", grid[i][j].getSymbol());
                System.out.print(content + "|");
            }
            System.out.println("\n" + "-".repeat(SIZE * 6));
        }
    }

    public List<Penguin> getPenguins() { return penguins; }

    public void simulateTurn(Penguin p, Direction d, boolean useSpecial) {
        if (!p.isAlive()) return;
        if (p.isStunned()) {
            System.out.println(p.getSymbol() + " is stunned and skips turn!");
            p.recover();
            return;
        }

        System.out.println(p.getSymbol() + " moves " + d);

        if (useSpecial) {
            p.performSpecialAction(this, d); // Polymorphism
        } else {
            p.setMaxSlideDistance(SIZE);
        }

        slideObject(p, d, useSpecial && (p instanceof RockhopperPenguin));
        p.setMaxSlideDistance(SIZE); // Reset
    }

    private void slideObject(GameObject obj, Direction d, boolean rockhopperJump) {
        int dr = 0, dc = 0;
        switch(d) {
            case UP: dr = -1; break;
            case DOWN: dr = 1; break;
            case LEFT: dc = -1; break;
            case RIGHT: dc = 1; break;
        }

        boolean sliding = true;
        int steps = 0;
        int limit = (obj instanceof Penguin) ? ((Penguin)obj).getMaxSlideDistance() : SIZE;

        while (sliding && steps < limit) {
            int nextR = obj.getRow() + dr;
            int nextC = obj.getCol() + dc;

            if (!isInBounds(nextR, nextC)) {
                handleFallWater(obj);
                return;
            }

            ITerrainObject target = grid[nextR][nextC];

            if (target == null) {
                moveObjectDirectly(obj, nextR, nextC);
                steps++;
            } else {
                if (target instanceof Food && obj instanceof Penguin) {
                    moveObjectDirectly(obj, nextR, nextC);
                    Penguin p = (Penguin) obj;
                    Food f = (Food) target;
                    p.addFood(f);
                    foods.remove(f);
                    System.out.println(p.getSymbol() + " ate " + f.getName());
                    sliding = false;
                } 
                else if (target instanceof Hazard) {
                    if (rockhopperJump && obj instanceof RockhopperPenguin) {
                        int jumpR = nextR + dr;
                        int jumpC = nextC + dc;
                        if (isInBounds(jumpR, jumpC) && grid[jumpR][jumpC] == null) {
                            System.out.println("Rockhopper jumped over hazard!");
                            moveObjectDirectly(obj, jumpR, jumpC);
                            rockhopperJump = false;
                            steps++;
                            continue;
                        } else {
                            System.out.println("Rockhopper failed jump!");
                            handleCollision(obj, (GameObject) target, d);
                            sliding = false;
                        }
                    } else {
                        handleCollision(obj, (GameObject) target, d);
                        sliding = false;
                    }
                } 
                else if (target instanceof Penguin) {
                    System.out.println("Collision with Penguin!");
                    sliding = false;
                    Penguin other = (Penguin) target;
                    slideObject(other, d, false);
                }
                else { sliding = false; }
            }
        }
    }

    private void handleCollision(GameObject mover, GameObject obstacle, Direction dir) {
        if (obstacle instanceof HoleInIce) {
            HoleInIce hole = (HoleInIce) obstacle;
            if (hole.isPlugged()) {
                moveObjectDirectly(mover, obstacle.getRow(), obstacle.getCol());
            } else {
                if (mover instanceof Penguin) {
                    System.out.println("Penguin fell in hole!");
                    ((Penguin) mover).kill();
                    grid[mover.getRow()][mover.getCol()] = null;
                } else {
                    System.out.println("Hole plugged by " + mover.getSymbol());
                    hole.plug();
                    grid[mover.getRow()][mover.getCol()] = null;
                }
            }
        }
        else if (obstacle instanceof HeavyIceBlock) {
            System.out.println("Hit Heavy Ice Block!");
            if (mover instanceof Penguin) ((Penguin) mover).removeLightestFood();
        }
        else if (obstacle instanceof LightIceBlock) {
            System.out.println("Hit Light Ice Block!");
            if (mover instanceof Penguin) ((Penguin) mover).stun();
            slideObject(obstacle, dir, false);
        }
        else if (obstacle instanceof SeaLion) {
            System.out.println("Hit SeaLion! BOUNCE!");
            Direction opposite = getOppositeDir(dir);
            slideObject(mover, opposite, false);
            slideObject(obstacle, dir, false);
        }
    }

    private void handleFallWater(GameObject obj) {
        System.out.println(obj.getSymbol() + " fell into water!");
        if (obj instanceof Penguin) ((Penguin) obj).kill();
        grid[obj.getRow()][obj.getCol()] = null;
    }

    public void moveObjectDirectly(GameObject obj, int r, int c) {
        if (grid[obj.getRow()][obj.getCol()] == obj) {
            grid[obj.getRow()][obj.getCol()] = null;
        }
        obj.setPosition(r, c);
        grid[r][c] = obj;
    }

    public boolean isInBounds(int r, int c) { return r >= 0 && r < SIZE && c >= 0 && c < SIZE; }
    public boolean isValidMove(int r, int c) { return isInBounds(r, c) && grid[r][c] == null; }
    
    private Direction getOppositeDir(Direction d) {
        if(d==Direction.UP) return Direction.DOWN;
        if(d==Direction.DOWN) return Direction.UP;
        if(d==Direction.LEFT) return Direction.RIGHT;
        return Direction.LEFT;
    }
}