package group10.algorithm;

import java.util.Random;

/**
 * Created by gb22 on 2015-03-22.
 *
 */
public class algorithm {
    //Chance of getting wild (1/value)
    static int wildChance=10;

    //Ints for handling the different slot images (1-12)
    int img1;
    int img2;
    int img3;
    //Int to make sure that losses and highest wins are less likely to repeat.
    int prevSpin;
    //Int to handle the result (1-12)
    int result;

    //Int to handle number of spins the user has left
    int spinsLeft;

    //Constructor
    public algorithm () {
        img1=0;
        img2=0;
        img3=0;
        prevSpin=0;
        spinsLeft=30;
    }

    public int get1() {
        return img1;
    }

    public int get2() {
        return img2;
    }

    public int get3() {
        return img3;
    }

    public int getResult() {
        return result;
    }

    public int getSpins() {
        return spinsLeft;
    }

    //Determines the result of next spin.
    public void spin () {
       /*
       Decide outcome.
        */
        Random rand=new Random();
        result=rand.nextInt(100)+1+prevSpin;

        /*
        Set the images
        Image 1-11 are pictures listed bellow. 12 is wild.
         */
        //Loss
        if (result<=10) {
            //Prevention of loss streak
            prevSpin+=1;

            img1=rand.nextInt(12)+1;
            if (img1==12) {
                img2=rand.nextInt(11)+1;
            }
            else {
                img2=rand.nextInt(12)+1;
            }

            if (img1==img2) {
                img3=rand.nextInt(11)+1;
                while (img1==img3) {
                    img3=rand.nextInt(11)+1;
                }
            }
            else if (img1==12 || img2==12) {
                img3=rand.nextInt(11)+1;
                while (img1==img3 || img2==img3) {
                    img3=rand.nextInt(11)+1;
                }
            }
            else {
                img3=rand.nextInt(12)+1;
            }
        }

        //Cherry (1)
        else if (11<=result && result<=27) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=1;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=1;
            if (rand.nextInt(wildChance)+1==1 && (img1==1 || img2==1))
                img3=12;
            else
                img3=1;
        }

        //Pine tree (2)
        else if (28<=result && result<=38) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=2;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=2;
            if (rand.nextInt(wildChance)+1==1 && (img1==2 || img2==2))
                img3=12;
            else
                img3=2;
        }

        //"Bar" (3)
        else if (39<=result && result<=49) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=3;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=3;
            if (rand.nextInt(wildChance)+1==1 && (img1==3 || img2==3))
                img3=12;
            else
                img3=3;
        }

        //Bell (4)
        else if (50<=result && result<=58) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=4;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=4;
            if (rand.nextInt(wildChance)+1==1 && (img1==4 || img2==4))
                img3=12;
            else
                img3=4;
        }

        //Snowflake (5)
        else if (59<=result && result<=67) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=5;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=5;
            if (rand.nextInt(wildChance)+1==1 && (img1==5 || img2==5))
                img3=12;
            else
                img3=5;
        }

        //Mead tankard (6)
        else if (68<=result && result<=75) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=6;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=6;
            if (rand.nextInt(wildChance)+1==1 && (img1==6 || img2==6))
                img3=12;
            else
                img3=6;
        }

        //Snowball (7)
        else if (76<=result && result<=82) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=7;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=7;
            if (rand.nextInt(wildChance)+1==1 && (img1==7 || img2==7))
                img3=12;
            else
                img3=7;
        }

        //Moose (8)
        else if (83<=result && result<=88) {
            prevSpin=0;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=8;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=8;
            if (rand.nextInt(wildChance)+1==1 && (img1==8 || img2==8))
                img3=12;
            else
                img3=8;
        }

        //Coin (9)
        else if (89<=result && result<=93) {
            prevSpin=0;
            spinsLeft+=1;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=9;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=9;
            if (rand.nextInt(wildChance)+1==1 && (img1==9 || img2==9))
                img3=12;
            else
                img3=9;
        }

        //"7" (10)
        else if (94<=result && result<=98) {
            prevSpin=0;
            spinsLeft+=3;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=10;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=10;
            if (rand.nextInt(wildChance)+1==1 && (img1==10 || img2==10))
                img3=12;
            else
                img3=10;
        }

        //Barbarian "Bar" (11)
        else if (99<=result) {
            //Prevention of "high win" streak
            prevSpin-=1;
            spinsLeft+=10;
            if (rand.nextInt(wildChance)+1==1)
                img1=12;
            else
                img1=11;
            if (rand.nextInt(wildChance)+1==1)
                img2=12;
            else
                img2=11;
            if (rand.nextInt(wildChance)+1==1)
                img3=12;
            else
                img3=11;
        }

        else {
            System.out.println("Result Error.");
        }
    }
}
