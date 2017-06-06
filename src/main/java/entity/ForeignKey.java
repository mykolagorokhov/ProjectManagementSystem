package entity;

/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class ForeignKey {
    private Integer firstColum;
    private Integer secondColum;

    public ForeignKey(Integer firstColum, Integer secondColum) {
        this.firstColum = firstColum;
        this.secondColum = secondColum;
    }

    public Integer getFirstColum() {
        return firstColum;
    }

    public void setFirstColum(Integer firstColum) {
        this.firstColum = firstColum;
    }

    public Integer getSecondColum() {
        return secondColum;
    }

    public void setSecondColum(Integer secondColum) {
        this.secondColum = secondColum;
    }
}
