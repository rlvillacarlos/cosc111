package edu.java.cosc111.samples.lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class UpdatableBinaryMinHeapPQ<K, P extends Comparable<P>> implements Iterable<K>{
    private class HeapItem<K,P>{
        K key;
        int index;
        P priority;
        
        HeapItem(int index_,K key_, P priority_) {
            index = index_;
            key = key_;
            priority = priority_;
        }
    }
    
    private ArrayList<HeapItem<K,P>> heap;
    private Map<K,Integer> invIndex;
    private int nHeapSize;

    public UpdatableBinaryMinHeapPQ() {
        heap = new ArrayList<>();
        invIndex = new HashMap<>();
        heap.add(null);
        nHeapSize = 0;
    }

    public UpdatableBinaryMinHeapPQ(K[] items,P[] priorities) {
        this();
        for(int i =0;i<items.length;i++){
            heap.add(new HeapItem(i+1,items[i],priorities[i]));
            invIndex.put(items[i], i+1);
        }        
        nHeapSize = heap.size()-1;
        for (int i = nHeapSize / 2; i >= 1; i--) {
            demote(i);
        }
    }

    public void insert(K k,P p) {
        if(!invIndex.containsKey(k)){
            nHeapSize++;
            heap.add(nHeapSize, new HeapItem(nHeapSize, k, p));
            invIndex.put(k, nHeapSize);
            promote(nHeapSize);
        }
    }
    
    public P getPriority(K k){
        if(!invIndex.containsKey(k)){
            return null;
        }
        int i = invIndex.get(k);
        return heap.get(i).priority;
    }
    
    public int size(){
        return heap.size()-1;
    }
    
    public K getMin() {
        return heap.get(1).key;
    }
   
    public K removeMin() {
        if (nHeapSize > 0) {
            K max = heap.get(1).key;
            HeapItem<K,P> last = heap.get(nHeapSize); 
            heap.set(1, last);
            invIndex.replace(last.key, 1);
            heap.remove(nHeapSize);
            invIndex.remove(max);
            nHeapSize--;
            demote(1);
            return max;
        }
        throw new NoSuchElementException();
    }

    public void updatePriority(K k, P p) {
        if(invIndex.containsKey(k)){
            int index = invIndex.get(k);
            HeapItem<K,P> item = heap.get(index); 
            P oldPriority = item.priority;
            item.priority = p;
            int cmp = oldPriority.compareTo(p); 
            if(cmp>0){
                promote(index);
            }else if(cmp<0){
                demote(index);
            }
        }else{
            insert(k, p);
        }

    }

    private void promote(int i) {
        if (i > 1) {
            HeapItem<K,P> parent = heap.get(i / 2);
            HeapItem<K,P> cur = heap.get(i);

            while (i > 1 && cur.priority.compareTo(parent.priority) < 0) {
                heap.set(i / 2, cur);
                invIndex.replace(cur.key, i/2);
                heap.set(i, parent);
                invIndex.replace(parent.key, i);                
                i = i / 2;
                parent = heap.get(i / 2);
            }
        }
    }

    private void demote(int i) {
        while (i * 2 <= nHeapSize) {
            HeapItem<K,P> cur = heap.get(i);
            int nChild = i * 2;//left-child

            if (nChild < nHeapSize
                    && heap.get(nChild).priority.compareTo(
                            heap.get(nChild + 1).priority) > 0) {
                nChild++;//right-child
            }
            HeapItem<K,P> child = heap.get(nChild);
            if (!(cur.priority.compareTo(child.priority) > 0)) {
                break;
            }
            
            invIndex.replace(child.key, i);            
            heap.set(i, child);
            invIndex.replace(cur.key, nChild);            
            heap.set(nChild, cur);
            
            i = nChild;
        }
    }

    @Override
    public String toString() {
        String temp = "[";
        for (int i = 1; i < heap.size(); i++) {
            temp = temp + (heap.get(i).key.toString()) + " ";
        }
        if (temp.length() > 1) {
            temp = temp.substring(0, temp.length() - 1);
        }
        temp += "]";
        return temp;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int i = 0;
            private K cur = null;
            
            @Override
            public boolean hasNext() {
                return (i<heap.size()-1);
            }

            @Override
            public K next() {
                if(hasNext()){
                    cur = heap.get(++i).key;
                }
                return cur;                
            }
        };
    }
    
    public static void main(String[] args) {
        Integer[]   items={1,2,3,4,5,6};
        Float[] priorities={1.0f,2.0f,3.0f,
                            4.0f,5.0f,6.0f};
        UpdatableBinaryMinHeapPQ<Integer,Float> heap = new UpdatableBinaryMinHeapPQ<>(items, priorities);
        System.out.println(heap);
        heap.updatePriority(6, -0.3f);
        System.out.println(heap);    
        heap.removeMin();
        System.out.println(heap);    
      
    }
}
