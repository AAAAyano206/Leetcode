/*
 * 1653. 使字符串平衡的最少删除次数
 * 给你一个字符串 s ，它仅包含字符 'a' 和 'b' 
 * 你可以删除 s 中任意数目的字符，使得 s 平衡，当不存在下标对 (i,j) 满足 i<j ，且 s[i] = 'b' 的同时 s[j]= 'a' ，此时认为 s 是平衡的
 * 请你返回使 s 平衡的最少删除次数。
 */

// 最开始就感觉这道题做过，但是具体做法忘记了，依稀记得是统计 a 的前面有多少 b，b 的后面有多少 a
// 一看提交记录，确实做过，还是用 python 做的
// 自己的思路是完全错误的，所以直接使用了 题解 的逻辑，由于最终的结果一定是前面全是 a，后面全是 b
// 所以对于原有的字符串一定存在某一个分割位置，删除该位置前的所有 b 以及该位置后的所有 a，即可得到最终结果
// 使用前缀法统计每个每个位置前的所有 b，然后在主循环前先使用一次循环统计字符串中的 a 和 b 的数量即可

class Solution {
    public int minimumDeletions(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length, cntA = 0;
        for(char ch: arr)
            cntA += 'b' - ch;
        
        int curA = 0, curB = 0, ans = cntA;
        for(int i = 0; i < n; ++i) {
            if(arr[i] == 'a')
                ++curA;
            else
                ++curB;
            ans = Math.min(ans, curB + cntA-curA);
        }
        return ans;
    }
}

// 题解里面灵神把后面主循环里的 if-else 判断改成了 cntA += (arr[i]-'a')*2 - 1; ans = Math.min(ans, cntA);
// 默认就是要删掉后面所有的 a，所以最开始就是 cntA 和 ans 进行比较，但是如果遍历过程中遍历到了 a，这个 a 就相当于是在前缀中，就不需要删除了，所以 cntA-1
// 如果遍历过程中遇到了 b，这个前缀中的 b 是需要删除的，所以 cntA+1
// 这里是使用 ASCII 码的处理去掉了判断分支，防止 CPU 在判断失败时重新进行回滚和加载导致额外开销，从而提升运行速度，但是会使可读性变差

class Solution {
    public int minimumDeletions(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length, cntA = 0;
        for(char ch: arr)
            cntA += 'b' - ch;
        int ans = cntA;
        for(int i = 0; i < n; ++i) {
            cntA += (arr[i]-'a')*2 - 1;
            ans = Math.min(ans, cntA);
        }
        return ans;
    }
}

// 这道题还有 DP 解法，就只需要一次遍历
// 遍历过程可以看作字符串的生长过程，如果当前字符为 b，那么是不需要进行删除操作的，因为它在最后
// 如果当前字符是 a，那就可以有两种选项，删除和不删除，删除的话就只需要在前一个字符的删除次数上 +1 即可
// 如果不删除这个 a 的话，就要删除它前面的所有 b，因此还需要额外统计 b 的数量

class Solution {
    public int minimumDeletions(String s) {
        int dp = 0, cntB = 0;
        for (char ch: s.toCharArray())
            if (ch == 'b') 
                ++cntB; // f 值不变
            else
                dp = Math.min(dp + 1, cntB);
        return dp;
    }
}
