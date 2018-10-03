package com.shorturl.shared.util.baseconvertor;

import org.apache.commons.lang3.StringUtils;

public class BaseConvertor {

  /**
   * Converts from base 10 to any base
   *
   * @return Encoded number
   * @Param base : output base
   * @Param number: number To Be Converted
   * @Param characters : String of characters
   **/
  public static String encode(Long base, Long number, String characters) {
    StringBuilder encodedString = new StringBuilder();
    while (number > 0) {
      encodedString.insert(0, getCharValue((int) (number % base), characters));
      number = number / base;
    }
    return encodedString.toString();
  }

  /**
   * Converts from given base to any base 10
   *
   * @return decoded String
   * @Param base : input base
   * @Param word: string To Be Converted
   * @Param characters : String of characters
   **/
  public static Long decode(Long base, String word, String characters) {
    Long decodedNo = 0L;
    for (int i = 0; i < word.length(); i++) {
      decodedNo = decodedNo * base + characters.indexOf(word.charAt(i));
    }
    return decodedNo;
  }

  /**
   * Finds the integer value of a character
   *
   * @return corresponding  value
   * @Param value : character whose int values is required
   * @Param characters : String of characters
   */
  private static int getIntValue(char value, String characters) {
    if (Character.isDigit(value)) {
      return (int) value - '0';
    } else if (Character.isAlphabetic(value)) {
      int positionA = StringUtils.indexOf(characters, 'A', 8);
      if (Character.isUpperCase(value)) {
        return (int) value - 'A' + positionA;
      } else {
        return (int) value - 'a' + (positionA <= 10 ? (positionA + 26) : (positionA - 26));
      }
    } else {
      return (int) value;
    }
  }

  /**
   * Finds the character value of a int
   *
   * @return corresponding chracter value
   * @Param value : int value whose char value is required
   * @Param characters : String of characters
   */
  private static char getCharValue(int value, String characters) {
    return characters.charAt(value);
  }

}
