package src.main.core;

import java.util.ArrayList;
import java.util.Random;

import src.main.Direction;
import src.main.boxes.Box;
import src.main.boxes.FixedBox;
import src.main.boxes.RegularBox;
import src.main.boxes.UnchangingBox;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.BoxFixer;
import src.main.tools.BoxFlipper;
import src.main.tools.MassColumnStamp;
import src.main.tools.MassRowStamp;
import src.main.tools.PlusShapeStamp;
import src.main.tools.SpecialTool;

public class BoxGrid {
    private ArrayList<ArrayList<Box>> grid;

    public ArrayList<ArrayList<Box>> getGrid() {
        return grid;
    }

    private Random random;
    private final int ROW_COUNT = 8;

    public int getRowCount() {
        return ROW_COUNT;
    }

    private final int COL_COUNT = 8;

    public int getColCount() {
        return COL_COUNT;
    }

    public BoxGrid() {
        this.grid = new ArrayList<>();
        this.random = new Random();
        initializeGrid();
    }

    public Box getBox(int row, int col) {
        if (row > ROW_COUNT - 1 || col > COL_COUNT - 1) {
            throw new IllegalArgumentException("Invalid row or column index.");
        } else {
            return grid.get(row).get(col);
        }
    }

    public void setBox(int row, int col, Box newBox) {
        if (row > ROW_COUNT - 1 || col > COL_COUNT - 1) {
            throw new IllegalArgumentException("Invalid row or column index.");
        } else {
            grid.get(row).set(col, newBox);
        }
    }

    public void initializeGrid() {
        for (int i = 0; i < ROW_COUNT; i++) {
            ArrayList<Box> rowList = new ArrayList<>();
            for (int j = 0; j < COL_COUNT; j++) {
                Box box = generateRandomBox();
                assignRandomTool(box);
                rowList.add(j, box);
            }
            grid.add(rowList);
        }
    }

    private Box generateRandomBox() {
        int chance = random.nextInt(100);
        if (chance < 85) {
            return new RegularBox();
        } else if (chance < 95) {
            return new UnchangingBox();
        } else {
            return new FixedBox();
        }
    }

    private void assignRandomTool(Box box) {
        if (box instanceof RegularBox) {
            int chance = random.nextInt(100);
            if (chance < 75) {
                box.setTool(createRandomTool());
            }
        } else if (box instanceof UnchangingBox) {
            box.setTool(createRandomTool());
        }
    }

    private SpecialTool createRandomTool() {
        int chance = random.nextInt(5);
        switch (chance) {
            case 0:
                return new BoxFixer();
            case 1:
                return new BoxFlipper();
            case 2:
                return new MassColumnStamp();
            case 3:
                return new MassRowStamp();
            case 4:
                return new PlusShapeStamp();
            default:
                throw new IllegalStateException("Unexpected value: " + chance);
        }

    }

    // Gerekli importlar:
    // import src.main.Direction;
    // import src.main.exceptions.UnmovableFixedBoxException;

    public void rollGrid(int startRow, int startCol, Direction dir) throws UnmovableFixedBoxException {
        
        // 1. ADIM: Başlangıç kutusu FixedBox mı kontrolü [cite: 53]
        // Eğer oyuncunun seçtiği ilk kutu Fixed ise hareket hiç başlamaz ve hata fırlatılır.
        Box startBox = grid.get(startRow).get(startCol);
        if (startBox instanceof FixedBox) {
            throw new UnmovableFixedBoxException();
        }

        // 2. ADIM: Yöne göre döngü kurma ve Domino Etkisi [cite: 26, 29]
        switch (dir) {
            case RIGHT:
                // Seçilen sütundan sağa doğru sona kadar git
                for (int j = startCol; j < COL_COUNT; j++) {
                    if (!applyRollOrStop(startRow, j, dir)) break; 
                }
                break;

            case LEFT:
                // Seçilen sütundan sola (0'a) doğru git
                for (int j = startCol; j >= 0; j--) {
                    if (!applyRollOrStop(startRow, j, dir)) break;
                }
                break;

            case DOWN:
                // Seçilen satırdan aşağı doğru git
                for (int i = startRow; i < ROW_COUNT; i++) {
                    if (!applyRollOrStop(i, startCol, dir)) break;
                }
                break;

            case UP:
                // Seçilen satırdan yukarı (0'a) doğru git
                for (int i = startRow; i >= 0; i--) {
                    if (!applyRollOrStop(i, startCol, dir)) break;
                }
                break;
        }
    }

    /**
     * Yardımcı Metot: Tek bir kutuyu yuvarlamayı dener.
     * Eğer kutu FixedBox ise FALSE döner (Domino etkisini durdurmak için).
     * Değilse yuvarlar ve TRUE döner.
     */
    private boolean applyRollOrStop(int r, int c, Direction dir) {
        Box currentBox = grid.get(r).get(c);

        // Kural: FixedBox domino etkisini durdurur [cite: 52]
        // "It stops the domino-effect from being transmitted past it."
        if (currentBox instanceof FixedBox) {
            return false; // Döngüyü kır (break)
        }

        try {
            currentBox.roll(dir); // Kutuyu yuvarla [cite: 78]
        } catch (UnmovableFixedBoxException e) {
            // Buraya normalde düşmez çünkü yukarıda instanceof kontrolü yaptık.
            // Ama yine de güvenli kod için catch bloğu bırakıyoruz.
            return false;
        }
        
        return true; // Zincir devam etsin
    }

    public int calculateScore(char targetLetter) {
        int score = 0;

        // Tüm gridi gez [cite: 22, 106]
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                Box box = grid.get(i).get(j);
                
                // Kutunun üst yüzü hedef harfle aynı mı?
                if (box.getTopSide() == targetLetter) {
                    score++;
                }
            }
        }
        
        return score;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // 1. Sütun Başlıklarını Yazdır (C1, C2, ...)
        sb.append("      "); // Sol baştaki R1, R2 payı için boşluk
        for (int i = 1; i <= COL_COUNT; i++) {
            // Her sütun yaklaşık 9-10 karakter yer kaplıyor, ona göre hizalıyoruz
            sb.append(String.format("   C%d   ", i));
        }
        sb.append("\n");

        // 2. Üst Çizgi (Separator)
        sb.append("      ");
        for (int i = 0; i < COL_COUNT; i++) {
            sb.append("--------");
        }
        sb.append("\n");

        // 3. Satırları Yazdır
        for (int i = 0; i < ROW_COUNT; i++) {
            // Satır Başlığı (R1, R2...)
            sb.append(String.format(" R%d   |", (i + 1)));

            // O satırdaki kutuları yazdır
            for (int j = 0; j < COL_COUNT; j++) {
                Box box = grid.get(i).get(j);
                // Box sınıfındaki toString metodunu çağırıyoruz (| R-E-M | kısmı)
                sb.append(box.toString());
                sb.append("|");
            }
            sb.append("\n"); // Satır sonu

            // Ara Çizgi (Her satırın altına)
            sb.append("      ");
            for (int k = 0; k < COL_COUNT; k++) {
                sb.append("--------");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
