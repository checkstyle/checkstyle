package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedWhitespaceInsideArrayInitializer {

  public final ColorPicker[] color1 = {ColorPicker.Black};
  public final ColorPicker[] color2 = {ColorPicker.White};
  public final ColorPicker[] color3 = {ColorPicker.White, ColorPicker.Black};

  public final ColorPicker[][] color4 = {{ColorPicker.Black, ColorPicker.Midori}};
  public final ColorPicker[][] color5 = {{ColorPicker.Black, ColorPicker.Midori}};

  public final ColorPicker[][] color6 = {{ColorPicker.Black, ColorPicker.Midori}};

  public ColorPicker[][] color7 = {
    {ColorPicker.Yellow, ColorPicker.Orange}, // false-negative, ok until #3203
  };

  public ColorPicker[][][] color8 = {
    {{ColorPicker.Black, ColorPicker.White, ColorPicker.Yellow}, {ColorPicker.Black}, {}}
  };

  /** Some javadoc. */
  public enum ColorPicker {
    Black,
    White,
    Yellow,
    Orange,
    Midori
  }
}
