package com.mec.engine.gfx;

public class Font
{
    /*Fonts, to create one just use the font creator and add the generated file here. 
    The fonte creator is located inside res/Fonts.
    */
    public static final Font STD_FONT = new Font("/res/Fonts/Std_Font.mcf");
    public static final Font COMICSANS_FONT = new Font("/res/Fonts/Comic_Font.mcf");

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path)
    {
        fontImage = new Image(path);
        offsets = new int[256]; //59
        widths = new int[256];

        int unicode = 0;
        for(int i = 0; i < fontImage.getWidth(); i++)
        {
            if(fontImage.getPixels()[i] == 0xff0000ff) offsets[unicode] = i;
            if(fontImage.getPixels()[i] == 0xffffff00)
            {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {return this.fontImage;}
    public void setFontImage(Image fontImage) {this.fontImage = fontImage;}

    public int[] getOffsets() {return this.offsets;}
    public void setOffsets(int[] offsets) {this.offsets = offsets;}

    public int[] getWidths() { return this.widths;}
    public void setWidths(int[] widths) {this.widths = widths;}
}