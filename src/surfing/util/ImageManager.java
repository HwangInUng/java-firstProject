package surfing.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {

	//이미지 배열 이용
	public Image[] createImages(String[] imgName) {
		Class myClass = this.getClass();
		Image[] img = new Image[imgName.length];
		
		for (int i = 0; i < imgName.length; i++) {
			// 자원의 위치를 의미, 패키지 경로도 처리 가능
			URL url = myClass.getClassLoader().getResource(imgName[i]);

			try {
				img[i] = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return img;
	}
	
	//ArrayList 이용
	public ArrayList<Image> createImg(String[] imgName) {
		Class myClass = this.getClass();
		ArrayList<Image> img = new ArrayList<Image>();
		
		for (int i = 0; i < imgName.length; i++) {
			URL url = myClass.getClassLoader().getResource(imgName[i]);
			
			try {
				img.add(ImageIO.read(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	//이미지 아이콘 생성
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass = this.getClass();
		URL url = myClass.getClassLoader().getResource(path);
		Image scaledImg = null;
		
		try {
			Image img = ImageIO.read(url);
			scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		ImageIcon icon = new ImageIcon(scaledImg);
		return icon;
	}
	
	//이미지 생성
	public Image getImage(String path, int width, int height) {
		URL url = this.getClass().getClassLoader().getResource(path);
		Image image = null;
		
		try {
			image = ImageIO.read(url);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	//파일 삭제 메서드
	public static boolean deleteFile(String path) {
		File file = new File(path);
		boolean result = file.delete();
		return result;
	}
}
