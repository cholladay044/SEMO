import java.util.ArrayList;
public class ThreadSort extends Thread{
    private ArrayList<Integer> list;
    
    public ThreadSort(ArrayList<Integer> list){
        this.list = list;
    }
    public void sort(){
        threadMergeSort(this.list);
    }
    private static void merge(ArrayList<Integer> list, ArrayList<Integer> list2, ArrayList<Integer> list3){
          //set up a temporary arraylist to build the merge list
        ArrayList<Integer> temp = new ArrayList<>();

        //set up index values for merging the two lists
        int current1 = 0;    
        int current2 = 0;
        int current3 = 0;

        while (current2 < list2.size() && current3 < list3.size()) {
            if (list2.get(current2) > list3.get(current3) ) {
                    list.set(current1, list2.get(current2));
                    current2++;
                } else {
                    list.set(current1, list3.get(current3));
                    current3++;
                }
                current1++;
        }

        int tempIndex = 0;
        if (current2 >= list2.size()) {
            temp = list3;
            tempIndex = current3;
        }
        else {
            temp = list2;
            tempIndex = current2;
        }

        for (int i = tempIndex; i < temp.size(); i++) {
            list.set(current1, temp.get(i));
            current1++;
        }  
    }
    public void threadMergeSort(ArrayList<Integer> list){
        ArrayList<Integer> firstHalf = new ArrayList<Integer>();
        ArrayList<Integer> secondHalf = new ArrayList<Integer>();
        
        int half;
        if (list.size() >1){
            half = list.size()/2;
            Thread t1 = new Thread(){
              @Override
              public void run(){
                  for(int i=0; i<half; i++){
                      firstHalf.add(list.get(i));
                  }
              }
            };
            Thread t2 = new Thread(){
              @Override
              public void run(){
                  for(int i=half; i<list.size()-1; i++){
                      secondHalf.add(list.get(i));
                  }
              }
            };
            t1.start();
            t2.start();
            
            try{
                t1.join();
                t2.join();
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
            threadMergeSort(firstHalf);
            threadMergeSort(secondHalf);
            merge(list, firstHalf, secondHalf);
        }
    }
    public ArrayList<Integer> getList(){
        return this.list;
    }
}
