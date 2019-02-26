package guiblockchain;

import java.util.GregorianCalendar;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author andrerib
 */
public final class Block {

    public String hash; //Hash attuale
    private final String data; //dati del blocco
    public final String PrevHash; //Hash precedente
    private final long TimeStamp; //ora di creazione
    private final GregorianCalendar TimeStamp2; //ora di creazione

    /**
     * Costruttore
     *
     * @param prev hash precedente
     * @param data messaggio
     */
    public Block(String prev, String data) {
        PrevHash = prev;
        this.data = data;
        TimeStamp2 = new GregorianCalendar();
        TimeStamp = TimeStamp2.getTime().getTime();
        hash();
    }

    /**
     * Costruttore
     *
     * @param prev hash precedente
     */
    public Block(String prev) {
        PrevHash = prev;
        this.data = Crypto.getRandomMessage(); //X paga Y N euro
        TimeStamp2 = new GregorianCalendar();
        TimeStamp = TimeStamp2.getTime().getTime();
        hash();
    }
    
    /**
     * Getter
     *
     * @return hash del blocco
     */
    public String getHash() {
        return hash;
    }

    /**
     * getter
     *
     * @return messaggio del blocco
     */
    public String getData() {
        return data;
    }

    /**
     * getter
     *
     * @return hash del blocco precedente
     */
    public String getPrevHash() {
        return PrevHash;
    }

    /**
     * getter
     *
     * @return data e ora di creazione del blocco
     */
    public long getTimeStamp() {
        return TimeStamp;
    }

    /**
     * getter
     *
     * @return stringa di data e ora di crezione del blocco
     */
    public String getTimeStamp2() {
        return TimeStamp2.getTime().toString();
    }

    /**
     * Calcola l'hash del blocco corrente
     *
     * @return this.hash
     */
    public String hash() {
        return (hash = Crypto.SHA256(PrevHash + TimeStamp + data));
    }

    /**
     * Ritorna il blocco sottoforma di stringa
     *
     * @return {ID, PREVIOUS_HASH, HASH}
     */
    @Override
    public String toString() {
        return ("Block info:\n"
                + "   Prev = " + PrevHash + "\n"
                + "   Hash = " + hash + "\n"
                + "   Msg  = " + data + "\n"
                + "   Data = " + getTimeStamp2());
    }

    /**
     * Ricrea il blocco corrente sottoforma di interfaccia grafica
     * @return VBox del blocco
     */
    public VBox getGraphics() {
        VBox vbox = new VBox();
        vbox.setMinSize(620, 100);
        Label _hash, prevhash, time;
        TextArea _data;
        _hash = new Label("   HASH: " + this.hash);
        prevhash = new Label("   HASH PREC.: " + PrevHash);
        time = new Label("   ORA: " + getTimeStamp2() + "\n");
        _data = new TextArea(this.data);
        _data.setTranslateX(_data.getLayoutX() + 10);
        _data.setEditable(false);
        _data.setMaxSize(278, 87);
        vbox.setMinHeight(130);
        vbox.getChildren().addAll(_hash, _data, prevhash, time);
        vbox.setBorder(new Border(new BorderStroke(Paint.valueOf("#b3b3b3"),
                BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        //Azione al clic del mouse
        vbox.setOnMouseClicked(event -> {
            BlockInfoController.blocco = this;
            BlockInfoController.update();
            View.selected.setBackground(Background.EMPTY);
            View.selected = vbox;
            View.selected.setBackground(new Background(new BackgroundFill(Paint.valueOf("#bbbbee"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        return vbox;
    }
}
