package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class RegexDemo implements RegexExc {

  @Override
  public boolean matchJpeg(String filename) {
    Pattern pattern1 = Pattern.compile("\\w+\\.jpe?g", Pattern.CASE_INSENSITIVE);
    Matcher matcher1 = pattern1.matcher(filename);
    // System.out.println(matcher1.group()); // print the matched string
    return matcher1.matches();
  }

  @Override
  public boolean matchIp(String ip) {
    return false;
  }

  @Override
  public boolean isEmptyLine(String line) {
    return false;
  }

  public static void main(String[] args) {
    String filename = "my_picture.jpg";
    String ip = "";
    String line = "";

    RegexDemo regexDemo = new RegexDemo();
    System.out.prin


  }

}