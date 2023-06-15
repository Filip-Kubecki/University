import java.util.ArrayList;
import java.util.Collections;

public class Algorithms {
    public int FRAME_SIZE;
    ArrayList<Page> OdwolaniaDoPamieci = new ArrayList<Page>();
    ArrayList<Page> Frame = new ArrayList<Page>();
    public int pageFailureCount = 0;
    public Algorithms(int FRAME_SIZE, ArrayList<Page> PageReferences) {
        this.FRAME_SIZE = FRAME_SIZE;
        this.OdwolaniaDoPamieci = PageReferences;
//        for (Page pageReference : PageReferences) {
//            System.out.println(pageReference.nr);
//        }
    }

    public int FIFO() {
        pageFailureCount = 0;
        ArrayList<Page> Pages1 = new ArrayList<>();

        for(Page p : OdwolaniaDoPamieci){
            Pages1.add(new Page(p));
        }

        Page tempPage;
        for (int i = 0; i < Pages1.size(); i++) {
            tempPage = Pages1.get(i);
            loop:
            if (Frame.size() < FRAME_SIZE) {
                for (Page page : Frame){
                    if (page.nr == tempPage.nr){
                        break loop;
                    }
                }
                Frame.add(tempPage);
                pageFailureCount++;
            }else{
                for (Page p : Frame){
                    if (p.nr == tempPage.nr){
                        break loop;
                    }
                }
                    Frame.remove(0);
                    Frame.add(tempPage);
                    pageFailureCount++;
            }
        }
        Frame.clear();
        return pageFailureCount;
    }
    public int OPT(){
        pageFailureCount = 0;
        ArrayList<Page> Pages2 = new ArrayList<>();
        for(Page p : OdwolaniaDoPamieci){
            Pages2.add(new Page(p));
        }
        Page tempPage;
        for (int i = 0; i < Pages2.size(); i++) {
            tempPage = Pages2.get(i);
            mainloop:
            if (Frame.size() < FRAME_SIZE) {
                for (Page p : Frame){
                    if (p.nr == tempPage.nr){
                        break mainloop;
                    }
                }
                pageFailureCount++;
                Frame.add(tempPage);
            }else {
                for (Page p : Frame) {
                    if (p.nr == tempPage.nr) {
                        break mainloop;
                    }
                }
                Page m = latest(tempPage, Pages2, Frame);
                if(m != null){
                    Frame.remove(m);
                }else {
                    Frame.remove(0);
                }
                Frame.add(tempPage);
                pageFailureCount++;
            }
        }
        Frame.clear();
        return pageFailureCount;
    }
    public int LRU(){
        pageFailureCount = 0;
        ArrayList<Page> Pages2 = new ArrayList<>();
        for(Page p : OdwolaniaDoPamieci) {
            Pages2.add(new Page(p));
        }
        Page tempPage;
        for (int i = 0; i < Pages2.size(); i++) {
            tempPage = Pages2.get(i);
            mainloop:
            if (Frame.size() < FRAME_SIZE) {
                for (Page p : Frame) {
                    if (p.nr == tempPage.nr) {
                        p.setRef(p.ref+1);
                        break mainloop;
                    }
                }
                pageFailureCount++;
                Frame.add(tempPage);
            }else {
                for (Page p : Frame){
                    if (p.nr == tempPage.nr) {
                        p.setRef(p.ref+1);
                        break mainloop;
                    }
                }
                Collections.sort(Frame, Page.refComparator);
                Frame.remove(0);
                Frame.add(tempPage);
                pageFailureCount++;
            }
        }
        Frame.clear();
        return pageFailureCount;
    }
    public int ALRU(){
        pageFailureCount = 0;
        ArrayList<Page> Pages1 = new ArrayList<>();
        for(Page p : OdwolaniaDoPamieci){
            Pages1.add(new Page(p));
        }
        Page tempPage;
        for (int i = 0; i < Pages1.size(); i++) {
            tempPage = Pages1.get(i);
            mainloop:
            if (Frame.size() < FRAME_SIZE) {
                for (Page p : Frame){
                    if (p.nr == tempPage.nr){
                        p.setParityBit(true);
                        p.setRef(p.ref+1);
                        break mainloop;
                    }
                }
                pageFailureCount++;
                Frame.add(tempPage);
            }else {
                for (Page p : Frame){
                    if (p.nr == tempPage.nr){
                        p.setParityBit(true);
                        p.setRef(p.ref+1);
                        break mainloop;
                    }
                }
                Collections.sort(Frame, Page.refComparator);
                boolean found = false;
                parityLoop:
                do {
                    for (Page p : Frame) {
                        if (p.parityBit == false) {
                            Frame.remove(p);
                            Frame.add(tempPage);
                            break parityLoop;
                        } else {
                            p.setParityBit(false);
                        }
                    }
                }
                while(found == false);
                pageFailureCount++;

            }
        }
        Frame.clear();
        return pageFailureCount;
    }
    public int RANDOM(){
        pageFailureCount = 0;
        ArrayList<Page> Pages2 = new ArrayList<>();
        for(Page p : OdwolaniaDoPamieci){
            Pages2.add(new Page(p));
        }
        Page tempPage;
        for (int i = 0; i < Pages2.size(); i++){
            tempPage = Pages2.get(i);
            mainloop:
            if (Frame.size() < FRAME_SIZE) {
                for (Page p : Frame) {
                    if (p.nr == tempPage.nr) {
                        break mainloop;
                    }
                }
                pageFailureCount++;
                Frame.add(tempPage);
            }else {
                for (Page p : Frame){
                    if (p.nr == tempPage.nr) {
                        break mainloop;
                    }
                }
                int r =(int)(Math.random()*FRAME_SIZE);
                Frame.set(r, tempPage);
                pageFailureCount++;

            }
        }
        Frame.clear();
        return pageFailureCount;
    }
    public Page latest(Page p, ArrayList<Page> a, ArrayList<Page> frameCopy)    {
        ArrayList <Page> temp = new ArrayList<>();
        for(Page page :frameCopy) {
            temp.add(new Page(page));
        }

        for(int i = a.indexOf(p); i < a.size(); ++i) {
            for(int j = 0; j < temp.size(); j++) {
               if(temp.get(j).nr == a.get(i).nr) {
                    temp.remove(j);
                }
                if(temp.size() == 1) {
                    for (int k = 0; k < frameCopy.size(); k++) {
                        if (temp.get(0).nr == frameCopy.get(k).nr)
                            return frameCopy.get(k);
                    }
                }
            }
       }
        return null;
    }
}