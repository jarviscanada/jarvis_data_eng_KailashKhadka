package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class RegexDemo implements RegexExc {

  @Override
  public boolean matchJpeg(String filename) {
    //for both filename and extension: regex = "\\w+\\.jpe?g"
    // for extension only (?<=.)jpe?g i.e. jpg or jpeg preceded by a dot
    Pattern pattern1 = Pattern.compile("\\w+\\.jpe?g", Pattern.CASE_INSENSITIVE);
    Matcher matcher1 = pattern1.matcher(filename);
    // System.out.println(matcher1.group()); // print the matched string
    return matcher1.matches();
  }

  @Override
  public boolean matchIp(String ip) {
    String regex1 = "(\\d{1,3}\\.){3}\\d{1,3}";
    Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
    Matcher matcher1 = pattern1.matcher(ip);
    // System.out.println(matcher1.group()); // print the matched string
    return matcher1.matches();
  }

  @Override
  public boolean isEmptyLine(String line) {
    String regex1 = "\\s*"; //zero or more whitespaces
    //String regex1 = "(?<=\\n)\\s+(?=\\n)"; whitespaces between newlines, excluding both newlines
    Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
    Matcher matcher1 = pattern1.matcher(line);
    // System.out.println(matcher1.group()); // print the matched string
    return matcher1.matches();
  }

  public static void main(String[] args) {
    String filename = "my_picture.jpcg";
    String ip = "01.125.1.0";
    String line = "          ";

    RegexDemo regexDemo = new RegexDemo();
    System.out.println(filename + " has extension jpg or jpeg. Case insensitive\t" + regexDemo.matchJpeg(filename));
    System.out.println(ip + " is a valid ip address. \t" + regexDemo.matchIp(ip));
    System.out.print(line + " is an empty line.\t" + regexDemo.isEmptyLine(line));
  }

}