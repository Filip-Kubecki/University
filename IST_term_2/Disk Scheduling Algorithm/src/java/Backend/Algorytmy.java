package Backend;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

public class Algorytmy {
    public static GraphData FCFS(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;           //Ilość zgłoszeń
        int timer = 0;          //Czas zegara
        int headPosition = 0;   //Pozycja głowicy
        int headMovements = 0;  //Suma przesunięć głowicy
        int totalWaitingTime = 0;  //Suma czasu oczekiwania
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();

        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }

//          Obsługa zgłoszeń
            if (queue.size() != 0){
                Request report = queue.get(0);
                headMovements += abs(report.getSektorDysku() - headPosition);
                headPosition = report.getSektorDysku();
                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));

//                Zwiększenie czasu oczekiwania procesów w kolejce
                for (int i = 1; i < queue.size(); i++) {
                    queue.get(i).IncreaseCzasOczekiwania(report.getCzasWykonania());
                }
                timer += report.getCzasWykonania();
                queue.remove(0);
                sizeQueueEnd++;
            }else{
                timer++;
            }
        }while(size > sizeQueueEnd);
        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(0);
        return output;
    }

    public static GraphData SSTF(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;          //Ilość zgłoszeń wykonanych
        int timer = 0;                 //Czas zegara
        int headPosition = 0;          //Pozycja głowicy
        int headMovements = 0;         //Suma przesunięć głowicy
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();

        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }
//          Obsługa zgłoszeń
            if (queue.size() != 0){
//                Znajdywanie najbliższego zgłoszenia
                int indexOf = 0;//Index najbliższego sektora w kolejce
                for (Request request : queue) {
                    if (abs(request.getSektorDysku()-headPosition)<abs(queue.get(indexOf).getSektorDysku()-headPosition)){
                        indexOf = queue.indexOf(request);
                    }
                }

                Request report = queue.get(indexOf);
                headMovements += abs(report.getSektorDysku() - headPosition);
                headPosition = report.getSektorDysku();

                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));

//                Zwiększenie czasu oczekiwania procesów w kolejce
                for (int i = 1; i < queue.size(); i++) {
                    queue.get(i).IncreaseCzasOczekiwania(report.getCzasWykonania());
                }
                timer += report.getCzasWykonania();
                queue.remove(indexOf);
                sizeQueueEnd++;
            }else{
                timer++;
            }
        }while(size > sizeQueueEnd);
        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(0);
        return output;
    }

    public static GraphData SCAN(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;          //Ilość zgłoszeń wykonanych
        int timer = 0;                 //Czas zegara
        int headPosition = 0;          //Pozycja głowicy
        int headMovements = 0;         //Suma przesunięć głowicy
        boolean headDirection = true;  //true = right, false = left
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();

        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }
//            Wyszukiwanie zgłoszeń w miejscu głowicy
            int indexOf = -1;
            for (Request request : queue) {
                if (request.getSektorDysku() == headPosition){
                    indexOf = queue.indexOf(request);
                }
            }
//            Wykonanie zgłoszeń w przypadku wykrycia
            if (indexOf != -1){
                Request request = queue.get(indexOf);

                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));

                timer += request.getCzasWykonania();
                queue.remove(request);
                sizeQueueEnd++;
            }else{
                if (headPosition == discSize){
                    headDirection = false;
                } else if (headPosition == 0) {
                    headDirection = true;
                }
                if (headDirection)
                    headPosition++;
                else
                    headPosition--;

                timer++;
                headMovements++;
            }
        }while(size > sizeQueueEnd);
        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(0);
        return output;
    }

    public static GraphData CSCAN(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;          //Ilość zgłoszeń wykonanych
        int timer = 0;                 //Czas zegara
        int headPosition = 0;          //Pozycja głowicy
        int headMovements = 0;         //Suma przesunięć głowicy
        boolean headDirection = true;  //true = right, false = left
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();

        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }
//            Wyszukiwanie zgłoszeń w miejscu głowicy
            int indexOf = -1;
            for (Request request : queue) {
                if (request.getSektorDysku() == headPosition){
                    indexOf = queue.indexOf(request);
                }
            }
//            Wykonanie zgłoszeń w przypadku wykrycia
            if (indexOf != -1){
                Request request = queue.get(indexOf);
                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));
                timer += request.getCzasWykonania();
                queue.remove(request);
                sizeQueueEnd++;
//                System.out.println("Kierunek "+headDirection+" Size:"+size+" Queue Size:"+sizeQueueEnd);
            }else{
                if (headPosition == discSize){
                    headPosition = 0;
                    headMovements ++;
                }else{
                    headPosition++;
                    timer++;
                    headMovements++;
                }
            }
        }while(size > sizeQueueEnd);

        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(0);
        return output;
    }
//    Real time algorithms
    public static GraphData EDF(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;           //Ilość zgłoszeń
        int timer = 0;          //Czas zegara
        int headPosition = 0;   //Pozycja głowicy
        int headMovements = 0;  //Suma przesunięć głowicy
        int countKilledRequests = 0;
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();


        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }

//          Obsługa zgłoszeń
            if (queue.size() != 0){
                Collections.sort(queue);
                Request report = queue.get(0);
                headMovements += abs(report.getSektorDysku() - headPosition);
                headPosition = report.getSektorDysku();
                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));

//                Zwiększenie czasu oczekiwania procesów w kolejce
                int waitingTime = report.getCzasWykonania();
                for (int i = queue.size()-1; i > 0; i--) {
                    Request tempReport = queue.get(i);
                    tempReport.IncreaseCzasOczekiwania(waitingTime);
                    tempReport.decreaseDeadline(waitingTime);
                    if (tempReport.endOfDeadline()){
                        queue.remove(tempReport);
                        sizeQueueEnd++;
                        countKilledRequests++;
                    }
                }
                timer += waitingTime;
                queue.remove(0);
                sizeQueueEnd++;
            }else{
                timer++;
            }
        }while(size > sizeQueueEnd);
        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(countKilledRequests);
        return output;
    }

    public static GraphData  FD_SCAN(ArrayList<Request> zgloszenia, int size, int discSize){
        ArrayList<Request> zgloszeniaCopy = (ArrayList<Request>) zgloszenia.clone();
        ArrayList<Request> queue = new ArrayList<>();
        int sizeQueueEnd = 0;          //Ilość zgłoszeń wykonanych
        int timer = 0;                 //Czas zegara
        int headPosition = 0;          //Pozycja głowicy
        int headMovements = 0;         //Suma przesunięć głowicy
        int countKilledRequests = 0;
        boolean headDirection = true;  //true = right, false = left
        ArrayList<TimeToHeadPosition> headPositionChangesInTime = new ArrayList<>();
        GraphData output = new GraphData();


        do {
//            Dodawanie procesów do kolejki
//            dodawanie na podstawie ich momentu zgłoszenia przyrównanego do czasu zegara
            for (int i = zgloszeniaCopy.size()-1; i > -1; i--) {
//                Jeśli czas zgłoszenia pokrywa się z czasem zegara, dodaj proces do kolejki (queue) i usuń z ArrayListy(zgłoszenia)
                if (zgloszeniaCopy.get(i).getMomentZgloszenia() <= timer){
                    queue.add(zgloszeniaCopy.get(i));
                    zgloszeniaCopy.remove(zgloszeniaCopy.get(i));
                }
            }

//            Wyszukiwanie zgłoszeń w miejscu głowicy
            int indexOf = -1;
            for (Request request : queue) {
                if (request.getSektorDysku() == headPosition){
                    indexOf = queue.indexOf(request);
                }
            }
//            Wykonanie zgłoszeń w przypadku wykrycia
            if (indexOf != -1){
                Request request = queue.get(indexOf);

                headPositionChangesInTime.add(new TimeToHeadPosition(timer,headPosition));

                timer += request.getCzasWykonania();
                queue.remove(request);
                sizeQueueEnd++;
            }else{
                if (headPosition == discSize)
                    headDirection = false;
                else if (headPosition == 0)
                    headDirection = true;

                if (queue.size() != 0){
                    if (closestDeadlineDirection(queue,headPosition) == 1)
                        headDirection = true;
                    else if (closestDeadlineDirection(queue,headPosition) == -1) {
                        headDirection = false;
                    }
                }

                if (headDirection)
                    headPosition++;
                else
                    headPosition--;
                timer++;
                headMovements++;
            }
            if (queue.size() != 0){
                for (int i = queue.size()-1; i > 0; i--) {
                    Request tempReport = queue.get(i);
                    tempReport.IncreaseCzasOczekiwania(1);
                    tempReport.decreaseDeadline(1);
                    if (tempReport.endOfDeadline() && tempReport.haveDeadline){
                        queue.remove(tempReport);
                        sizeQueueEnd++;
                        countKilledRequests++;
                    }
                }
            }
        }while(size > sizeQueueEnd);
//        Wpisywanie danych do klasy z wynikami
        output.setTimeToHeadPositionArray(headPositionChangesInTime);
        output.setAllHeadMovements(headMovements);
        output.setTotalTime(timer);
        output.setAmountOfKilledRequest(countKilledRequests);
        return output;
    }

    public static int closestDeadlineDirection(ArrayList<Request> zgloszenia, int headPosition){
        int temp = 0;
//        Find shortest deadline
        for (Request request : zgloszenia) {
            if (request.getDeadline() > zgloszenia.get(temp).getDeadline())
                temp = zgloszenia.indexOf(request);
        }
//        Return direction of deadline: 1 - right, -1 - left,
        if (zgloszenia.get(temp).getSektorDysku() == headPosition)
            return 0;

        return zgloszenia.get(temp).getSektorDysku() > headPosition ? 1 : -1;
    }
}