package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputWhitespaceInsideArrayInitializer {

  // false-negative below, ok until #3203
  public final ColorPicker[] color1 = { ColorPicker.Black};

  // false-negative below, ok until #3203
  public final ColorPicker[] color2 = {ColorPicker.White };

  // false-negative below, ok until #3203
  public final ColorPicker[] color3 = { ColorPicker.White, ColorPicker.Black };

  // false-negative below, ok until #3203
  public final ColorPicker[][] color4 = { {ColorPicker.Black, ColorPicker.Midori} };

  // 2 false-negatives below, ok until #3203
  public final ColorPicker[][] color5 = { { ColorPicker.Black, ColorPicker.Midori } };

  // false-negative below, ok until #3203
  public final ColorPicker[][] color6 = {{ ColorPicker.Black, ColorPicker.Midori }};

  public ColorPicker[][] color7 = {
    { ColorPicker.Yellow, ColorPicker.Orange }, // false-negative, ok until #3203
  };

  // false-negative below, ok until #3203
  public ColorPicker[][][] color8 = {
      { {ColorPicker.Black, ColorPicker.White, ColorPicker.Yellow}, {ColorPicker.Black}, {} }
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
