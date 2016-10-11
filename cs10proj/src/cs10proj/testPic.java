package cs10proj;

public class testPic{
	public static void main(String[] args){
		String name = new String(FileChooser.pickAFile());
    Picture origPic = new Picture(name);
    origPic.explore();
	}
}