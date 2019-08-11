package IteratorPattern;


import java.util.Iterator;

public class ConcreteAggregate implements Aggregate {
    Object[] objects;
    int index =0;
    public ConcreteAggregate(Object[] objects){
        this.objects = objects;
    }
    @Override
    public Iterator getIterator() {
        return new InnerClass();
    }
    public class InnerClass implements Iterator{

        @Override
        public boolean hasNext() {
            return index< objects.length;
        }

        @Override
        public Object next() {
            if (hasNext())
                return objects[index++];
            else return null;
        }
    }
    public static void main(String args[]){
        ConcreteAggregate concreteAggregate = new ConcreteAggregate(new Integer[]{1, 2, 3});
        Iterator iterator =concreteAggregate.getIterator();
        while(iterator.hasNext()){
            System.out.println( iterator.next());
        }
    }
}
