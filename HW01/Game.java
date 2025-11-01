public class Game {
    private int id;
    private String gameName;
    private int basePoint;

    public Game(int id,String gameName,int basePoint){
        this.id = id;
        this.gameName = gameName;
        this.basePoint = basePoint;
    }
    public int getId() {return this.id;}
    public String getGameName(){return this.gameName;}
    public int getBasePoint(){return this.basePoint;}

    @Override
    public String toString(){
        return "ID => "+ id + " GameName => " + gameName + " BasePoint => "+basePoint;
    }
}
