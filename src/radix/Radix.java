package radix;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.*;

public class Radix extends Group{

    private ArrayList<Integer> array; // Niz brojeva
    private ArrayList<RectangleNumber> rNums; // Niz objekata(pravougaonika) sa brojevima
    private ArrayList<RectangleNumber> bucketsList; // Niz objekata bucket-a

    /**
     * Konstruktor
     * @param nums
     */
    public Radix(ArrayList<Integer> nums){
        this.array = nums;
        generateComponents();
    }

    public void generateComponents(){
        this.bucketsList = generateRectangles(generateDifferentBuckets(array),50,180,230,
                Color.GRAY, Color.YELLOW, Color.YELLOW, false);
        this.rNums = generateRectangles(array, 50, 30, 10, Color.GRAY, Color.BEIGE, Color.BLUEVIOLET, true);
    }

    /**
     * Generise listu svih razlicitih cifara koje se javljaju u svim brojevima za sortiranje
     * @param nums
     * @return
     */
    public static ArrayList<Integer> generateDifferentBuckets(ArrayList<Integer> nums){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);

        for(int num : nums){
            while (num != 0) {
                int tmp = num % 10;

                if(!result.contains(tmp))
                    result.add(tmp);

                num /= 10;
            }
        }
        Collections.sort(result);

        return result;
    }

    /**
     * Sortira niz brojeva sa osnovom 10
     */
    public void sort() {
        sort(10);
    }

    /**
     * Sortira niz brojeva sa odredjenom osnovom
     * @param radix = 10 - dekadni brojevi
     */
    public void sort(int radix) {
        if (array.size() == 0)
            return;

        int maxValue = findMax();

        SequentialTransition st = new SequentialTransition();
        int exponent = 1;
        while (maxValue / exponent > 0) {
            countingSortByDigit(radix, exponent, st);
            exponent *= radix;
        }
        st.play();
    }

    /**
     * Pronalazi maksimum u listi
     * @return
     */
    public int findMax(){
        int maxValue = array.get(0);
        for (int i = 1; i < array.size(); i++)
            if (array.get(i) > maxValue)
                maxValue = array.get(i);

        return maxValue;
    }

    /**
     * Sortiranje elemenata - jedan korak
     * @param radix - osnova brojeva
     * @param exponent - koja je cifra po redu
     * @param sequentialTransition - sekvencijalna tranzicija za translacije
     */
    private void countingSortByDigit(int radix, int exponent, SequentialTransition sequentialTransition) {
        int bucketIndex;
        ArrayList<Integer> output = new ArrayList<>();
        Map<Integer, ArrayList<RectangleNumber>> mapBuckets = new HashMap<>(); //key - broj bucket-a; value - elementi bucket-a

        //Inicijalizacija mape i brojaca za svaki bucket
        for (int i = 0; i < bucketsList.size(); i++) {
            int digit = Integer.parseInt(bucketsList.get(i).getString());
            mapBuckets.put(digit, new ArrayList<>());
        }

        //Rasporedjivanje elemenata u bucket-e
        for (int i = 0; i < array.size(); i++) {
            bucketIndex = (array.get(i) / exponent) % radix;

            for(RectangleNumber b : bucketsList){
                int bNum  = Integer.parseInt(b.getString());

                if(bNum == bucketIndex){
                    RectangleNumber rn = rNums.get(i);
                    createTranslation(rn, b, sequentialTransition, mapBuckets.get(bucketIndex).size(), false);
                    mapBuckets.get(bucketIndex).add(rn);
                    break;
                }
            }
        }
        rNums.clear();

        // Translacija elemenata iz bucket-a u novi niz za sortiranje u sledecem koraku
        int count = 0;
        for(int i : new TreeSet<>(mapBuckets.keySet())){

            for(RectangleNumber rn :  mapBuckets.get(i)) {
                RectangleNumber b =  new RectangleNumber("",50d,30d, Color.WHITE, Color.WHITE, Color.WHITE, true);
                b.setTranslateX(20);
                b.setTranslateY(20);
                b.setOpacity(0);
                this.getChildren().add(b);
                createTranslation(rn, b, sequentialTransition, count, true);
                count++;

                output.add(Integer.parseInt(rn.getString()));
                rNums.add(rn);
            }
        }

        // Kopiranje nazad u pocetni niz, sada su u array elementi posle ovog koraka
        for (int i = 0; i < array.size(); i++)
            array.set(i, output.get(i));

    }

    /**
     * Kreira translaciju
     * @param from - pocetni objekat
     * @param to - krajnji objekat
     * @param st - sekvencijalna translacija
     * @param index - indeks u nizu elemenata posle translacije
     * @param isHorizontal - da li je raspored elemenata horizontalan posle translacije
     */
    public void createTranslation(RectangleNumber from, RectangleNumber to, SequentialTransition st, Integer index, boolean isHorizontal){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), from);

        if(isHorizontal){
            double valueX = to.getBoundsInParent().getMinX() + index * to.getBoundsInParent().getWidth() + 40;
            tt.setToX(valueX);
            tt.setToY(to.getBoundsInParent().getMinY());
        } else {
            tt.setToX(to.getBoundsInParent().getMinX());
            tt.setToY(to.getBoundsInParent().getMinY() + to.getBoundsInParent().getHeight()
                    - 1.65 * from.getBoundsInParent().getHeight() - index * from.getBoundsInParent().getHeight());
        }
        st.getChildren().add(tt);
    }

    /**
     * Generise niz brojeva u pravougaonicima
     * @param nums
     * @param width
     * @param height
     * @param setToY
     * @param colorBorder
     * @param colorInside
     * @param textColor
     * @param isCenterText
     * @return
     */
    public ArrayList<RectangleNumber> generateRectangles(ArrayList<Integer> nums, double width, double height, double setToY,
                                                      Paint colorBorder, Paint colorInside, Paint textColor, boolean isCenterText){
        int n = nums.size();
        ArrayList<RectangleNumber> result = new ArrayList<>();
        ArrayList<Double> positionsX = new ArrayList<>();

        double d = 10d;
        for(int i = 0; i < n; i ++){
            positionsX.add(d);
            RectangleNumber rn = new RectangleNumber(nums.get(i).toString(), width, height, colorBorder, colorInside, textColor, isCenterText);
            result.add(rn);
            d += rn.getBoundsInParent().getWidth() + 10;
        }

        for(int i = 0; i < n; i ++){
            RectangleNumber rn = result.get(i);
            rn.setTranslateX(positionsX.get(i));
            rn.setTranslateY(setToY);
            this.getChildren().add(rn);
        }

        return result;
    }

}
