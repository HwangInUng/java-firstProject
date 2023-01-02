package surfing.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	public static String getNumString(int num) {
		String str = null;

		if (num < 10) {
			str = "0" + num;
		} else {
			str = Integer.toString(num);
		}
		return str;
	}
	
	//파일명 생성
	public static String createFileName(URL url) {
		
		String ext = StringUtil.getExtend(url.toString()); //이미지 주소의 확장자명 추출
		long time = System.currentTimeMillis(); //현재시간을 기준으로 파일명 지정
		
		String fileName = time + "." + ext;
		
		return fileName;
	}

	// 확장자 추출 후 반환 받기
	public static String getExtend(String fileName) {

		int lastIndex = fileName.lastIndexOf(".");

		return fileName.substring(lastIndex + 1, fileName.length());
	}

	// 비밀번호 암호화 하기
	public static String getCovertedPassword(String pass) {
		// 자바 보안관련 기능을 지원하는 API가 모여있는 패키지 = java.security
		StringBuffer hexString = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));

			// String은 불변! 따라서 값이 변경될 수 없음
			// 누적해야하는 경우 : StringBuffer 또는 StringBuilder 객체 사용
			hexString = new StringBuffer();

			for (byte bt : hash) {
				String hex = Integer.toHexString(0xff & bt);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

}
