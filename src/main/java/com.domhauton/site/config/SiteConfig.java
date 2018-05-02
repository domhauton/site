package com.domhauton.site.config;

import java.util.Objects;

/**
 * Created by dominic on 27/05/17.
 */

public class SiteConfig {

  private int test1 = 60;

  SiteConfig() {
    // Jackson ONLY
  }

  public int getTest1() {
    return test1;
  }

  public void setTest1(int test1) {
    this.test1 = test1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SiteConfig that = (SiteConfig) o;
    return test1 == that.test1;
  }

  @Override
  public int hashCode() {

    return Objects.hash(test1);
  }
}
