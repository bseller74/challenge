import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChallengeMain {

	public static void main(String[] args) throws FileNotFoundException {

		Map<String, Integer> map = new HashMap<>();
		List<String> sortedMap = new ArrayList<>();
		List<String> topTen = new ArrayList<>();
		List<String> sentenceList = new ArrayList<>();
		String topWord = null;
		String last = null;

		try (Scanner userInput = new Scanner(System.in)) {
			// Get the path of the input file, did not want to hard code one file ..passage.txt
			File inputFile;
			while (true) {
				System.out.println("What is the file that should be searched?");
				String path = userInput.nextLine();
				inputFile = new File(path);
				if (inputFile.exists() == false) {
					System.out.println(path + " does not exist");
					continue;
				} else if (inputFile.isFile() == false) {
					System.out.println(path + " is not a file");
					continue;
				}
				break;
			}
			int wordCount = 0;
			int sentenceCount = 0;
			try (Scanner inputScanner = new Scanner(inputFile.getAbsoluteFile())) {
				while (inputScanner.hasNextLine()) {
					String tempLine = inputScanner.nextLine();
					String line = tempLine.replace("\"", "");
					String line2 = line.replace(".", "");
					String line3 = line2.replace("?", "");
					String line4 = line3.replace(":", "");
					String line5 = line4.replace(",", "");
					String noPunctuation = line5.replace("!", "");
					String compareWords = noPunctuation.toLowerCase();

					if (!line.isEmpty() && !line.equals(" ")) {

						String[] words = line.trim().split("\\s+");
						String[] wordsWithout = compareWords.trim().split("\\s+");
						for (String countWord : wordsWithout) {
							Integer n = map.get(countWord);
							n = (n == null) ? 1 : ++n;
							map.put(countWord, n);
						}

						sortedMap = map.entrySet().stream()
								.sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
								.map(Map.Entry::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

						topTen = sortedMap.subList(0, 10);
						topWord = topTen.get(0);

						String[] sentences = line.split("[.?!]+");
						for (String sentence : sentences) {

							if (sentence.contains(topWord)) {
								sentenceList.add(sentence);
								last = sentenceList.get(sentenceList.size() - 1);
							}

						}

						wordCount += words.length;
						for (String word : words) {
							if (word.endsWith(".") || word.endsWith("?") || word.endsWith("!")) {
								sentenceCount += 1;
							}
						}

					}
				}
			}

			int count = 0;

			System.out.println("Word count: " + wordCount);
			System.out.println("Top Ten Words Used: ");
			for (String word : topTen) {
				count++;
				System.out.println(count + ". " + word);
			}
			System.out.println("Last sentence the top word was used: ");
			System.out.println(last);

		}

	}

}
