package com.common.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rahul Mangal 16-Nov-2019 - 3:48:58 PM
 */

@Service
public class UFile {

	public static final String temp_dir = System.getProperty("java.io.tmpdir");

	public static void saveOnLocalMachine(String destination, MultipartFile file) {
		try {
			file.transferTo(new File(destination));
		} catch (IllegalStateException | IOException e) {

			UtilityException.throwErr("Unable to save file. Please try again.");
		}
	}

	/**
	 * @param file
	 * @return
	 */
	public File saveTemporary(MultipartFile file, String filename) {

		String destination = UFile.temp_dir + File.separator + filename;
		File exactPath = new File(destination);
		exactPath.deleteOnExit();
		try {
			file.transferTo(exactPath);
		} catch (IllegalStateException | IOException e) {

			UtilityException.throwErr("Unable to save file, Please try again.");
		}
		return exactPath;
	}

	public String saveFile(MultipartFile file) {
		return saveFile(file, temp_dir);
	}

	/**
	 * @param file
	 * @return
	 */
	public String saveFile(MultipartFile file, String dir) {

		File directory = new File(dir);
		if (!directory.exists()) {
			directory.mkdir();
		}

		String originalFileName = file.getOriginalFilename();
		originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = originalFileName + "_" + (int) new Date().getTime() + "." + extension;

		String fileNameWithPath = dir + File.separator + fileName;
		OutputStream outputStream = null;

		try {
			File files = new File(fileNameWithPath);
			outputStream = new FileOutputStream(files);

			// file.getInputStream();
			outputStream.flush();
			outputStream.close();

		} catch (IOException e) {

		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				throw new UtilityException(
						"saveFile() - File -" + originalFileName + " while closing input/outstram in finally block", e);
			}
		}
		return fileName;
	}

	/**
	 * Replaced File name of with new Date number for any given location in
	 * parameter for any type of given in filename parameter
	 * 
	 * @param fileName
	 * @param location
	 * 
	 * @throws NullPointerException
	 *             if arguments is null
	 * @throws IOExcption
	 *             || {@link RPAException} if arguments is invalid
	 * @return New File Name that is replaced for existing file. else null if
	 *         file with @param filename not exist on @param location.
	 */
	public String renameFile(String fileName, String location) {
		// validate weather file exist or not
		File file = new File(location + fileName);
		if (!file.exists() || !file.isFile()) {
			UtilityException.throwErr("File " + fileName + " not exist on" + location);

		}

		// change new name of existing file
		String newFileName = UDate.sf.format(new Date())
				+ fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
		File newFile = new File(location + newFileName);
		try {
			FileUtils.moveFileToDirectory(file, newFile, false);
		} catch (IOException e) {
			UtilityException.throwErr("renameFile  fileName - {} , location -" + fileName + " " + location);
			return null;
		}

		// Delete existing old file from location
		file.deleteOnExit();

		// return new name of file
		return newFile.getName();
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		file.deleteOnExit();
	}

}
