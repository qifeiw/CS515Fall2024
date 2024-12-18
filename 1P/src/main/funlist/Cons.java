package funlist;

import java.util.NoSuchElementException;

import static funlist.List.cons;
import static funlist.List.nil;

/** 	CS515 Assignment 1P
 Name: Qifei Wang
 Section: 2
 Date: 09/09/2024
 Collaboration Declaration: None
 Collaboration: None
 */


public final class Cons<T> implements List<T> {

  private final T head;
  private List<T> tail; // non-final because of take and join

  Cons(T head, List<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public String toString() {
      // TODO: Implement
      StringBuilder sb = new StringBuilder();
      sb.append("List(");
      sb.append(head);
      List<T> current = tail;
      while (current.nonEmpty()) {
          sb.append(",").append(current.head());
          current = current.tail();
      }
      sb.append(")");
      return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
      if (this == obj) {
          return true;
      }

      if (obj == null || getClass() != obj.getClass()) {
          return false;
      }

      List<?> other = (List<?>) obj;
      List<?> current = this;

      while (current.nonEmpty() && other.nonEmpty()) {
          if (!current.head().equals(other.head())) {
              return false;
          }
          current = current.tail();
          other = other.tail();
      }
      return current.isEmpty() && other.isEmpty();
  }

  @Override
  public int hashCode() {
      // TODO: Implement
      int result = 1;
      int prime = 17;
      List<T> current = this;

      while (current.nonEmpty()) {
          result = prime * result + (current.head() == null? 0: current.head().hashCode());
          current = current.tail();
      }
      return result;
  }

  public T head() {
      // TODO: Implement
    return this.head;
  }

  public List<T> tail() {
      // TODO: Implement
    return this.tail;
  }

  public boolean isEmpty() {
      // TODO: Implement
      return false;
  }

  public boolean nonEmpty() {
      // TODO: Implement
      return true;
  }

  public int length() {
      // TODO: Implement
      return 1 + tail.length();
  }

  public List<T> drop(int count) {
      // TODO: Implement
      if (count < 0) {
          throw new IllegalArgumentException("count can't be negative");
      }
       List<T> current = this;

      for (int i = 0; i < count; i++) {
          if (current.isEmpty()) {
              return current;
          }
          current = current.tail();
      }
      return current;
  }

  public List<T> reverse() {
      // TODO: Implement
      List<T> result = nil();
      List<T> current = this;

      while (current.nonEmpty()) {
          result = cons(current.head(), result);
          current = current.tail();
      }
      return result;
  }

  public T getAt(int index) {
      // TODO: Implement
      if (index < 0 ) {
          throw new IllegalArgumentException("Index can't be negative");
      }

      List<T> current = this;
      int currentIndex = 0;

      while (current.nonEmpty()) {
          if (currentIndex == index) {
              return current.head();
          }
          current = current.tail();
          currentIndex++;
      }
      throw new NoSuchElementException("Index: " + index + "is out of bounds");
  }

  public List<T> take(int count) {
      // TODO: Implement
      if (count < 0) {
          throw new IllegalArgumentException("count can't be negative");
      }
      if (count == 0) {
          return nil();
      }
      if (count == 1) {
          return cons(this.head, nil());
      }
      return cons(this.head, this.tail().take(count -1));
  }

  public List<T> join(List<T> other) {
      // TODO: Implement

      List<T> list = nil();
      int other_size = other.length();
      int curr_size = this.length();

      if ((other_size == 0) && (curr_size == 0)) {
          return list;
      } else if ((other_size == 0) || (curr_size == 0)) {
          if (other_size==0) {
              return this.take(curr_size);
          } else {
              return other.take(other_size);
          }
      }

      for (int i  = other_size - 1; i >= 0; i--) {
          list = cons(other.getAt(i), list);
      }

      for (int j = curr_size - 1; j >= 0; j--) {
          list = cons(this.getAt(j), list);
      }
      return list;
  }
  public List<T> append(T value) {
      // TODO: Implement
      List<T> list = cons(value, nil());

      if (this.isEmpty()) {
          return list;
      }


      for (int i = this.length() - 1; i >= 0; i--) {
          list = cons(this.getAt(i), list);
      }
      return list;
  }

  public T last() {
      // TODO: Implement
      List<T> current = this;
      while (current.tail() != nil()) {
          current = current.tail();
      }
      return current.head();
  }
}
