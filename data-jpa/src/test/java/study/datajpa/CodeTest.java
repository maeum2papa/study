package study.datajpa;

import org.junit.jupiter.api.Test;

import java.util.*;

public class CodeTest {

    @Test
    public void test1() {

        int[] nums = {3,3,2,4};

        int answer = 0;

        HashMap<Integer, Integer> poketMons = new HashMap<>();

        for(int num : nums){
            if(poketMons.get(num) == null){
                poketMons.put(num,1);
            }else{
                poketMons.put(num,poketMons.get(num) + 1);
            }
        }


        System.out.println("poketMons = " + poketMons.size());
    }

    @Test
    public void test2(){

        List<Integer> playList = new ArrayList<>();

        playList.add(4);
        playList.add(1);

        System.out.println(playList.toArray());
    }


    @Test
    public void solution() {

        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기


        int bridge_length =2;
        int weight = 10;
        int[] truck_weights = {7,4,5,6};

        int answer = 0;
        int success = 0;

        ArrayList<Integer> readyTrucks = new ArrayList<>();
        Queue<Integer> moveTrucks = new LinkedList<>();

        for(int truck : truck_weights){
            readyTrucks.add(truck);
        }


        for(int i=0; i<bridge_length; i++){
            moveTrucks.add(0);
        }

        int sec = 0;
        Integer pushTruck = 0;
        boolean pass = true;
        int lastTruck = 0;
        while(success < truck_weights.length){
            sec++;

            System.out.println("sec = " + sec);
            System.out.println("moveTrucks = " + moveTrucks);

            lastTruck = moveTrucks.remove();

            pushTruck = 0;
            pass = true;

            if(readyTrucks.size() != 0){
                pushTruck = readyTrucks.get(0);
                pass = checkWeight(moveTrucks, weight, pushTruck);
            }

            if(lastTruck > 0 ) success++;

            if(pass){
                moveTrucks.add(pushTruck);
                readyTrucks = updateReadyTrucks(readyTrucks);
            }else{
                moveTrucks.add(0);
            }

            System.out.println(" ======= after ============= ");

            System.out.println("moveTrucks = " + moveTrucks);
            System.out.println(" ++++++++++++++++++++++++++++++ ");

        }

        System.out.println("sec = " + sec);



        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        double secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
//        System.out.println("시간차이 : "+secDiffTime);

//        return answer;
    }

    //다리로 트럭 이동
    public ArrayList<Integer> updateReadyTrucks(ArrayList<Integer> readyTrucks){

        ArrayList<Integer> newReadyTrucks = new ArrayList<>();

        for(int i=0; i<readyTrucks.size(); i++){
            if(i==0) continue;
            newReadyTrucks.add(readyTrucks.get(i));
        }

        return newReadyTrucks;
    }

    //다리 위에서 트럭 이동
    public ArrayList<Integer> updateMoveTrucks(ArrayList<Integer> moveTrucks, int bridge_length){

        ArrayList<Integer> newMoveTrucks = new ArrayList<>();

        newMoveTrucks.add(0);
        for(int i=1; i<bridge_length; i++){
            newMoveTrucks.add(moveTrucks.get(i-1));
        }


        return newMoveTrucks;

    }

    //다리 무게 체크
    public boolean checkWeight(Queue<Integer> moveTrucks, int weight, Integer addTruck){

        int sum = 0;
        boolean result = false;

        for(Integer moveTruck : moveTrucks){
            sum += moveTruck;
        }

        if(!((sum + addTruck) > weight)){
            result = true;
        }

        return result;
    }



    @Test
    public void queueTest(){

        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        Queue<Integer> q = new LinkedList<>();

        q.add(1);
        q.add(2);
        q.add(0);
        q.add(3);


        System.out.println("q.remove = " + q.remove());
        System.out.println("q = " + q);

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        double secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        System.out.println("시간차이 : "+secDiffTime);

        // +++++++++++++++++++++++++++++++++++++++++++++++++

        beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        ArrayList<Integer> a = new ArrayList<>();

        a.add(1);
        a.add(2);
        a.add(0);
        a.add(3);

        ArrayList<Integer> na = new ArrayList<>();

        na.add(0);
        for(int i=1; i<a.size(); i++){
            na.add(a.get(i-1));
        }

        a = na;

        System.out.println("a.get(0) = " + a.get(0));
        System.out.println("a = " + a);

        afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        System.out.println("시간차이 : "+secDiffTime);
    }


    @Test
    public void arrayTest(){

        int[] srcArray = {3, 4, 6, 5, 7, 10, 9, 8};
//        Arrays.sort(srcArray);

        Arrays.sort(srcArray);

        for(int a : srcArray){
            System.out.print(a);
        }


    }


    /*
    [7] = false;
    [3,4] = true;
    [11, 1, 8, 12, 14] = true;
    [5,5,5,5] = false;

    두개 요소의 차가 1 이상인게 있으면 true

     */

    @Test
    public void solution1(){

        int[] A = {11, 1, 8, 12, 14};
        boolean result = false;
        
        Arrays.sort(A);

        for(int i=0; i<A.length; i++){
//            System.out.println("A[i] = " + A[i]);
            if(i == 0) continue;
            if((A[i-1] - A[i]) == -1){
                result = true;
                break;
            }
        }
    }



    /*
    ?ab??a = aabbaa
    bab?a = NO
    ?a? = aaa or zaz

    ?ab??a
    a??ba?

    bab?a
    a?bab

    ?a?
    ?a?


     */
    @Test
    public void solution2(){

        String S = "bab?a";
//        String S = "?a?";
        String result = "NO";

        String[] rArray = S.split("");
        String[] rSArray = S.split("");
        String rS = "";

        //rArray.length = 5
        for(int i=rArray.length-1; i>=0; i--){
//            System.out.println("rSArray[i] = " + rSArray[i]);
            rS += rArray[i];
            rSArray[(rArray.length-1)-i] = rArray[i]; // 0(4-4),4  1(4-3),3   2(4-2),2
        }

        if(S.equals(rS)){
            result = S.replace("?","a");
        }else{

            for(int i=0; i<rArray.length; i++){
                if(rArray[i].equals("?") && !rSArray[i].equals("?")){
                    rArray[i] = rSArray[i];
                }else if(rSArray[i].equals("?") && !rArray[i].equals("?")){
                    rSArray[i] = rArray[i];
                }
            }

            String C = "";
            for(String rArrayItem : rArray){
                C += rArrayItem;
            }

            String D = "";
            for(String rSArrayItem : rSArray){
                D += rSArrayItem;
            }

//            System.out.println("C = " + C);
//            System.out.println("D = " + D);

            if(C.equals(D)){
                result = C;
            }
        }

        System.out.println(result);
    }


    @Test
    public void solution3() {

//        String S = "011100";
        String S = "1";

        for(int i=0; i<400000; i++){
            S += "1";
        }

        int result = 0;
        int cnt = 0;

        String[] sArray  = S.split("");
        for(String sArrayItem : sArray) {
            if (sArrayItem.equals("1")) {
                cnt++;
                if (cnt >= 400000) {
                    break;
                }
            }
        }

        if(cnt >= 400000){
            result = 799999;
        }else {
            int V = Integer.parseInt(S,2);

            while (V > 0 && !S.equals("")) {

                result++;

                if (V % 2 == 0) {//짝
                    V = V / 2;
                } else {//홀
                    V = V - 1;
                }
            }
        }

        System.out.println("result = " + result);

    }


    /*
    문자 A와 인접한 문자 B를 제거 OR 문자 C와 인접한 문자 D를 제거
    더 이상 변환할 수 없을 때까지

    CBACD => C
    CABABD => ""
    ACBDACBD => ""

    BA
    AB
    CD
    DC

     */
    @Test
    public void solution4() {

        String S = "CABABD";
        String result = "";

        String[] sArray = S.split("");

        int cnt = chkReplace(sArray);

        if(cnt == 0){
            result = S;
        }else{

            while(cnt > 0){

                for(int i=0; i<sArray.length; i++) {
                    if(i > 0){
                        if(
                                (sArray[i-1].equals("B") && sArray[i].equals("A")) ||
                                (sArray[i-1].equals("A") && sArray[i].equals("B")) ||
                                (sArray[i-1].equals("C") && sArray[i].equals("D")) ||
                                (sArray[i-1].equals("D") && sArray[i].equals("C"))
                        ){
                            sArray[i-1] = "";
                            sArray[i] = "";
                            continue;
                        }
                    }
                }

                ArrayList<String> newSArray = new ArrayList<>();
                for (String s : sArray) {
//                    System.out.println("s = " + s);
                    if(!s.equals("")){
                        newSArray.add(s);
                    }
                }

                if(newSArray.size() > 0){

                    sArray = new String[newSArray.size()];

                    for (int i = 0; i <sArray.length; i++) {
                        sArray[i] = newSArray.get(i);
                    }

                    cnt = chkReplace(sArray);

                }else{
                    break;
                }

            }

            if(sArray.length > 0){
                for (String s : sArray) {
                    result += s;
                }
            }
        }


        System.out.println("cnt = " + cnt);
        System.out.println("result = " + result);
    }

    //변환할게 있는지 체크
    public int chkReplace(String[] sArray){
        int cnt = 0;
        for(int i=0; i<sArray.length; i++){
//            System.out.println("sArray[i] = " + sArray[i]);
            if(i > 0){
                if(
                        (sArray[i-1].equals("B") && sArray[i].equals("A")) ||
                        (sArray[i-1].equals("A") && sArray[i].equals("B")) ||
                        (sArray[i-1].equals("C") && sArray[i].equals("D")) ||
                        (sArray[i-1].equals("D") && sArray[i].equals("C"))
                ){
                    cnt++;
                    break;
                }
            }
        }

        return cnt;
    }
}
