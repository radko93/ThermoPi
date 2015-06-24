package zpi.thermopi;


import android.graphics.drawable.Drawable;

public class NavigationItem {
    private String mText;
    private Drawable mDrawable;
    private int Id;

    public NavigationItem(String text, Drawable drawable, int id) {
        mText = text;
        mDrawable = drawable;
        Id = id;
    }


    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public int getId() {
        return Id;
    }
}
