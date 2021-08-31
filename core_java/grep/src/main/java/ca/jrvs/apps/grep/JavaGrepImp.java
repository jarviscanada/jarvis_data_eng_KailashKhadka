package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger((JavaGrep.class));

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);
    javaGrepImp.logger.info("Into the try block of process function");

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Exception Caught", ex);
    }
  }

  /**
   * Top level search workflow
   *
   * @throws IOException if files can't be created, accessed, or written.
   */
  @Override
  public void process() throws IOException {
    /* pseudo-code
    matchedLines = []
    for file in listFilesRecursively(rootDir)
      for line in readLines(file)
          if containsPattern(line)
            matchedLines.add(line)
    writeToFile(matchedLines)
     */
    logger.debug("\nRegex:\t{}\nRoot Path:\t{}\nOutput File:\t{}\n",this.regex, this.rootPath, this.outFile);
    ArrayList<String> matchedLines = new ArrayList<>();
    for (File file : listFiles(this.rootPath)) {
      for (String line : readLines(file)) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
    logger.info("\nOutput successfully written to: {}", this.outFile);
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  @Override
  public List<File> listFiles(String rootDir) {
    ArrayList<File> listOfFiles = new ArrayList<>();
    File rootDirectory = new File(rootDir);
    if (rootDirectory.isDirectory()) {
      File[] fileArray = rootDirectory.listFiles();
      for (File file : fileArray) {
        if (file.isFile()) {
          listOfFiles.add(file);
        } else if (file.isDirectory()) {
          listOfFiles.addAll(listFiles(file.getAbsolutePath()));
        }
      }
    }
    return listOfFiles;
  }

  /**
   * Read a file and return all the lines
   * <p>
   * Explain FileReader, BufferReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  @Override
  public List<String> readLines(File inputFile) {
    ArrayList<String> fileContents = new ArrayList<>();
    BufferedReader reader;
    String line;
    try {
      reader = new BufferedReader(new FileReader(inputFile));
      line = reader.readLine();
      while (line != null) {
        fileContents.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      logger.error("Exception caught", e);
    }
    return fileContents;
  }

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  @Override
  public boolean containsPattern(String line) {
    //Pattern pattern1 = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE);
    Pattern pattern1 = Pattern.compile(this.regex);
    Matcher matcher1 = pattern1.matcher(line);
    return matcher1.matches();
  }

  /**
   * Write lines to a file
   * <p>
   * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    File fileOutput = new File(this.outFile);
    FileOutputStream fOpStr = new FileOutputStream(fileOutput);
    BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(fOpStr));
    for (String line : lines) {
      buffWriter.write(line);
      buffWriter.newLine();
    }
    buffWriter.close();
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}
