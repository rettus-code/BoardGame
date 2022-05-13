public class Take {
    private int locationX;
    private int locationY;
    private int height;
    private int width;
    private int number;
    private boolean complete;

    public Take(int x, int y, int h, int w, int num) {
        this.setLocationX(x);
        this.setLocationY(y);
        this.setHeight(h);
        this.setWidth(w);
        this.setNumber(num);
    }

    public int getLocationX() {
        return this.locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }

    public void setLocationX(int x) {
        if (x >= 0) {
            this.locationX = x;
        }
    }

    public void setLocationY(int y) {
        if (y >= 0) {
            this.locationY = y;
        }
    }

    public int getwidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int w) {
        if (w >= 0) {
            this.width = w;
        }
    }

    public void setHeight(int h) {
        if (h >= 0) {
            this.height = h;
        }
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int num) {
        if (num > 0 && num < 4) {
            this.number = num;
        }
    }

    public boolean isComplete() {
        return this.complete;
    }

    public void setComplete(boolean c) {
        this.complete = c;
    }

}