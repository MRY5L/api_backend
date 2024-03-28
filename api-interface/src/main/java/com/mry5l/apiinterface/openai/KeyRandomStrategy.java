package com.mry5l.apiinterface.openai;


import cn.hutool.core.util.RandomUtil;
import com.unfbx.chatgpt.function.KeyStrategyFunction;

import java.util.List;

public class KeyRandomStrategy implements KeyStrategyFunction<List<String>, String> {
  public String apply(List<String> apiKeys) {
    return RandomUtil.randomEle(apiKeys);
  }
}
