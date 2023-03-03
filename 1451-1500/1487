题目：
  给你一个长度为 n 的字符串数组 names 。你将会在文件系统中创建 n 个文件夹：在第 i 分钟，新建名为 names[i] 的文件夹。
  由于两个文件不能共享相同的文件名，因此如果新建文件夹使用的文件名已经被占用，系统会以 (k) 的形式为新文件夹的文件名添加后缀，其中 k 是能保证文件名唯一的最小正整数。
  返回长度为 n 的字符串数组，其中 ans[i] 是创建第 i 个文件夹时系统分配给该文件夹的实际名称。

 
 // 最开始的解法，最终结果是超时，因为有一个数据量很大的类似于这样的用例，names = ["kaido","kaido(1)","kaido","kaido(1)"]
 // 所以如果使用这种算法的话，当前缀一样的时候，会出现不断循环遍历相同后缀，直至找到符合题意的 k 的情况

 class Solution {
     public String[] getFolderNames(String[] names) {
         // 超时
         Set<String> set = new HashSet<>();
         int n = names.length;
         String[] ans = new String[n];
         for(int i = 0; i < n; ++i) {
             if(!set.contains(names[i])) {
                 ans[i] = names[i];
                 set.add(names[i]);
             }
             else {
                 int k = 1;
                 for(;;) {
                     String cur = names[i] + '(' + k + ')';
                     if(!set.contains(cur)) {
                         ans[i] = cur;
                         set.add(cur);
                         break;
                     }
                     else
                         ++k;
                 }
             }
         }
         return ans;
     }
}

// 遇到超时的结果后，首先考虑的是使用 map 存前缀和相应的“版本号 k ”，但是如何在字符串中分割出 k 变成了难题
// 首先想到的是，对于每个 names[i]，从后向前分割出最后一对的小括号（如果有的话），然后提出 k，并将对应的前缀放到 map 里面
// 比如 kaido(1)(1)(1)，就要存 [kaido(1)(1), 1] 和 [kaido(1)(1)(1), 0]，而没有后缀的情况，比如 kaido，就只需要存 [kaido, 0]
// 但是与之而来的还有这样的问题，比如 [kaido(1)(3), kaido(1)(1), kaido(1), kaido(1), kaido(1)]，所以添加了后缀以后还是要进行一次判断

// 后来在写代码的过程中发现，并不需要这么麻烦，因为既然添加了后缀之后还要判断的话，那反而不需要将 names[i] 去掉结尾小括号的值再放到 map 里了，避免重复判断

class Solution {
    public String[] getFolderNames(String[] names) {
        Map<String, Integer> map = new HashMap<>();
        int n = names.length;
        String[] ans = new String[n];
        for(int i = 0; i < n; ++i) {
            if(!map.containsKey(names[i])) {
                ans[i] = names[i];
                map.put(names[i], 0);
            }
            else {
                int k = map.get(names[i]) + 1;
                ans[i] = names[i] + '(' + k + ')';
                while(map.containsKey(ans[i])) {
                    ++k;
                    ans[i] = names[i] + '(' + k + ')';
                }
                map.put(names[i], k);
                map.put(ans[i], 0);
            }
        }
        return ans;
    }
}

// 题解：要更简洁一些

class Solution {
    public String[] getFolderNames(String[] names) {
        Map<String, Integer> d = new HashMap<>();
        for (int i = 0; i < names.length; ++i) {
            if (d.containsKey(names[i])) {
                int k = d.get(names[i]);
                while (d.containsKey(names[i] + "(" + k + ")")) {
                    ++k;
                }
                d.put(names[i], k);
                names[i] += "(" + k + ")";
            }
            d.put(names[i], 1);
        }
        return names;
    }
}

// 作者：lcbin
// 链接：https://leetcode.cn/problems/making-file-names-unique/solution/python3javacgo-yi-ti-yi-jie-ha-xi-biao-b-tv5h/
// 来源：力扣（LeetCode）
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
