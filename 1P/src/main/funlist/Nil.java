package funlist;

import java.util.NoSuchElementException;
import static funlist.List.cons;

/** 	CS515 Assignment 1P
 Name: Qifei Wang
 Section: 2
 Date: 09/09/2024
 Collaboration Declaration: None
 Collaboration: None
 */

public final class Nil<T> implements List<T> {

  private Nil() {
    // make Nil a singleton; equals and hashCode not redefined
    // INFO: Please do not change me :)
  }

  /* Must be defined here and not in List to avoid class initialization deadlocks. */
  final static Object TheNil = new Nil<Void>();

  @Override
  public String toString() {
      // TODO: Implement
      return "List()";
  }

  public T head() {
      // TODO: Implement
      throw new NoSuchElementException("head() called on empty list");
  }

  public List<T> tail() {
      // TODO: Implement
      throw new NoSuchElementException("tail() called at empty list.");
  }

  public T last() {
      // TODO: Implement
      throw new NoSuchElementException("last() called on empty list.");
  }

  public boolean isEmpty() {
      // TODO: Implement
      return true;
  }

  public boolean nonEmpty() {
      // TODO: Implement
      return false;
  }

  public int length() {
      // TODO: Implement
      return 0;
  }

  public List<T> take(int count) {
      // TODO: Implement
      if (count < 0) {
          throw new IllegalArgumentException("take() called with negative count");
      }
      return this;
  }

  public List<T> drop(int count) {
      // TODO: Implement
      if (count < 0) {
          throw new IllegalArgumentException("drop() called with negative count");
      }
      return this;
  }

  public List<T> reverse() {
      // TODO: Implement
      return this;
  }

  public T getAt(int index) {
      // TODO: Implement
      if (index < 0 ) {
          throw new IllegalArgumentException("getAt() called with negative index");
      }
      throw new NoSuchElementException("getAt() called on empty list");
  }

  public List<T> join(List<T> other) {
      // TODO: Implement
      return other;
  }

  public List<T> append(T value) {
      // TODO: Implement
      return cons(value, this);
  }
}
