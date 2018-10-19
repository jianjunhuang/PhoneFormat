package com.jianjunhuang.phoneformatdemo;

import android.text.Editable;
import android.text.TextWatcher;

public class PhoneFormatTextWatcher implements TextWatcher {
  private int beforeLen = 0;
  private int changeCount = 0;
  private boolean isAppendBlock = false;
  private boolean isDel = false;
  private boolean isReplaced = false;

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    beforeLen = s.length();
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    changeCount = count;
    isDel = (before != 0);
  }

  @Override
  public void afterTextChanged(Editable s) {
    String tmp = s.toString();
    char[] ch = tmp.toCharArray();
    if (changeCount > 1) {
      if (!isReplaced) {
        isReplaced = true;
        s.replace(0, s.length(), formatPhone(s.toString()));
        isReplaced = false;
      }
    } else {
      if (s.length() > 1 && (ch[s.length() - 1] > '9' || ch[s.length() - 1] < '0') &&
          !isAppendBlock) {
        s.delete(s.length() - 1, s.length());
      } else {
        if ((s.length() == 3 || s.length() == 8) && !isDel) {
          isAppendBlock = true;
          s.append(" ");
          isAppendBlock = false;
        }
      }
    }
  }

  private String formatPhone(String phone) {
    StringBuilder sb = new StringBuilder();
    char[] phoneChar = phone.toCharArray();
    for (char c : phoneChar) {
      if (c >= '0' && c <= '9') {
        sb.append(c);
        if (sb.length() == 3 || sb.length() == 8) {
          sb.append(" ");
        }
      }
    }
    return sb.toString();
  }

}
