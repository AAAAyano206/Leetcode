/*
 * 982.按位与为零的三元组
 * 给你一个整数数组 nums ，返回其中 按位与三元组 的数目。
 * 按位与三元组 是由下标 (i, j, k) 组成的三元组，并满足下述全部条件：
 * 0 <= i < nums.length
 * 0 <= j < nums.length
 * 0 <= k < nums.length
 * nums[i] & nums[j] & nums[k] == 0 ，其中 & 表示按位与运算符。
 */

// 最开始的想法肯定是直接暴力模拟，但是 O(n^3) 的时间复杂度一定会超时的
// 然后看示例可以发现他是“取完放回”形式，所以考虑使用 map 记录 nums 中每个元素出现的次数，然后再使用 O(n^3) 的时间复杂度进行遍历，将得到的次数进行相乘
// 不过这种解法也超时了，超时的用例中没有重复数字，只是数量比较多

class Solution {
    public int countTriplets(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for(int i = 0; i < n; ++i) {
            int tmp = map.getOrDefault(nums[i], 0);
            map.put(nums[i], tmp+1);
        }
        int ans = 0, size = map.size(), cnt = 0;
        int[][] arr = new int[size][2];
        for(Map.Entry<Integer, Integer> entry: map.entrySet())
            arr[cnt++] = new int[] {entry.getKey(), entry.getValue()};
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                for(int k = 0; k < size; ++k) {
                    if((arr[i][0] & arr[j][0] & arr[k][0]) == 0)
                        ans += arr[i][1] * arr[j][1] * arr[k][1];
                }
            }
        }
        return ans;
    }
}

// 考虑一下按位与的性质，三个数按位与最终结果为 0，说明至少在每个二进制位上，三个数中都有一个 0
// 而 nums[i] 最大值为 2^16，由此可以考虑使用 16 元组表示 nums 中的每一个数字，然后再解决问题
// 但是最终发现这种思路最终还是遍历然后判断，还不如直接按位与来得快

// 所以最终的思路避免不开将不同的值进行组合然后判断按位与的结果是不是 0 ，而这个 O(n^3) 的过程是可以降维的
// 先使用 map 或者 arr 去记录两两按位与的结果，然后再将 nums 和 map/arr 中的值进行按位与，可以将 O(n^3) 降维至 O(m*n^2)

class Solution {
    public int countTriplets(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                int temp = nums[i] & nums[j], times = map.getOrDefault(temp, 0)+1;
                map.put(temp, times);
            }
        }
        int ans = 0;
        for(int num: nums) {
            for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
                if((num & entry.getKey()) == 0)
                    ans += entry.getValue();
            }
        }
        return ans;
    }
}

// 题解：对上述 16 元组的思路进行扩展，枚举子集
// 因此，我们可以将 nums[k] 和 2^16-1 ，即(111111111111111) 进行按位异或运算
// 这个过程中满足要求的二元组的二进制表示中包含的 1 的位置必须是该数的子集
// 可以使用二进制枚举子集的技巧：
// 记该数为 m，我们用 s 表示当前枚举到的子集，初始时 s = m，因为 s 也是本身的子集
// 然后不断地令 s = (s-1) & m，这个过程中的 s 就是从大到小所得到的 m 的所有子集
// 之后再去找得到的子集对应的 cnt 就可以了

class Solution {
    public int countTriplets(int[] nums) {
        int[] cnt = new int[1 << 16];
        for (int x : nums)
            for (int y : nums)
                ++cnt[x & y];
        int ans = 0;
        for (int m : nums) {
            m ^= 0xffff;
            int s = m;
            do { // 枚举 m 的子集（包括空集）
                ans += cnt[s];
                s = (s - 1) & m;
            } while (s != m);
        }
        return ans;
    }
}

// 作者：endlesscheng
// 链接：https://leetcode.cn/problems/triples-with-bitwise-and-equal-to-zero/solution/you-ji-qiao-de-mei-ju-chang-shu-you-hua-daxit/
// 来源：力扣（LeetCode）
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
