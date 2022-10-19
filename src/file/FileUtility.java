package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FileUtility {
	static PrintWriter fileWriter;
//	    fileWriter.write("Hello Folks!");

	public static void main(String[] args) throws IOException, InterruptedException {
		fileWriter = new PrintWriter(new FileWriter("/home/mahendra/Desktop/thread.txt",true));

		ExecutorService es = Executors.newFixedThreadPool(3);
		IntStream.range(1, 10).forEach(a-> es.submit(FileUtility::threadTask));
		es.shutdown();
		Thread.currentThread().join();

//		new File("/home/mahendra/Desktop/directory").mkdirs();
//		deleteOlderFiles(0, "/home/mahendra/Desktop/clientUserPoint/");
		try {

//			System.out.println(Files.lines(Paths.get("/home/mahendra/Desktop/liveDetails.csv")).count());
//			System.out.println(isFileCreated("/home/mahendra/Desktop/clientUserPoint/NBTO_2022-08-26.csv"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void threadTask() {
		IntStream.range(1, 6).forEach(e -> {
			try {
				String str = Thread.currentThread().getName() + " : " + e;
				System.out.println(str);
				fileWriter.println(str);
				fileWriter.flush();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	public static boolean isFileCreated(String filePath) {
		Map<String, String> map = new HashMap<>();
		long lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			reader.readLine();
			String readLine = "";
			while ((readLine = reader.readLine()) != null) {
				lines++;
//	                System.out.println(readLine);
				String key = readLine.split(",")[0];
				if (map.containsKey(key))
					System.out.println(readLine);
				else
					map.put(key, readLine);

			}
		} catch (IOException e) {
		}
		return lines > 1;
	}

	public static void deleteOlderFiles(int daysBack, String path) {
		File directory = new File(path);
		try {
			if (directory.exists()) {
				long purgeTime = Instant.now().toEpochMilli() - (daysBack * 24 * 60 * 60 * 1000);
				for (File file : Objects.requireNonNull(directory.listFiles())) {
					BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
					if (attr.creationTime().toMillis() < purgeTime) {
						if (file.isFile() && file.delete())
							System.out.println("Deleted file: {} from path: {} successfully  " + file.getName() + " "
									+ file.getAbsolutePath());
						else if (file.isDirectory())
							deleteOlderFiles(daysBack, file.getPath());
					}
				}
			} else
				System.out.println("Files were not deleted, directory " + path + " does'nt exist!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
