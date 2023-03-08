/*
 * 1096. 花括号展开 II
 * 如果你熟悉 Shell 编程，那么一定了解过花括号展开，它可以用来生成任意字符串。
 * 花括号展开的表达式可以看作一个由 花括号、逗号 和 小写英文字母 组成的字符串，定义下面几条语法规则：
 * 如果只给出单一的元素 x，那么表达式表示的字符串就只有 "x"。R(x) = {x}
 * 例如，表达式 "a" 表示字符串 "a"。
 * 而表达式 "w" 就表示字符串 "w"。
 * 当两个或多个表达式并列，以逗号分隔，我们取这些表达式中元素的并集。R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
 * 例如，表达式 "{a,b,c}" 表示字符串 "a","b","c"。
 * 而表达式 "{{a,b},{b,c}}" 也可以表示字符串 "a","b","c"。
 * 要是两个或多个表达式相接，中间没有隔开时，我们从这些表达式中各取一个元素依次连接形成字符串。R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}
 * 例如，表达式 "{a,b}{c,d}" 表示字符串 "ac","ad","bc","bd"。
 * 表达式之间允许嵌套，单一元素与表达式的连接也是允许的。
 * 例如，表达式 "a{b,c,d}" 表示字符串 "ab","ac","ad","bd"。
 * 例如，表达式 "a{b,c}{d,e}f{g,h}" 可以表示字符串 "abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"。
 * 给出表示基于给定语法规则的表达式 expression，返回它所表示的所有字符串组成的有序列表。
 */

// 个人想法是使用迭代，一层一层的往外剥，将函数按照功能进行封装
// 首先是一个使用 ',' 完成分割的函数，然后是使用 "{}" 进行分割的函数，这样就可以分别进入乘入处理了，但是整体的关系没有梳理好，所以最后没写出来

class Solution {
    public List<String> braceExpansionII(String expression) {
        char[] arr = expression.toCharArray();
        List<String> list = new ArrayList<>();
        boolean notFinish = true;
        List<String> toSplit = new ArrayList<>();
        toSplit.add(expression);
        while(!toSplit.isEmpty()) {
            for(int i = 0; i < toSplit.size(); ++i) {
                String cur = toSplit.get(i);
                // 字符串的最开始一定是 '{' 或者是 字母
                if(cur.charAt(0) == '{') {
                    List<String> temp = split1(cur);
                    
                }

            }
        }

        // 简易去重
        Set<String> set = new HashSet<>();
        for(int i = 0; i < list.size(); ++i)
            set.add(list.get(i));
        List<String> ans = new ArrayList<>();
        for(String str: set) {
            ans.add(str);
        }
        return ans;
    }
    
    // 将给定的字符串按照 ',' 进行合理分离
    public List<String> split(String str) {
        List<String> res = new ArrayList<>();
        int left = 0, last = 0;
        char[] arr = str.toCharArray();
        for(int i = 0; i < arr.length; ++i) {
            if(arr[i] == '{')
                ++left;
            else if(arr[i] == '}')
                --left;
            else if(arr[i] == ',' && left == 0) {
                res.add(str.substring(last));
                last = i+1;
            }
        }
        res.add(str.substring(last, arr.length));
        return res;
    }

    // 将给定的字符串按照 {} 进行分离
    public List<String> split1(String str) {
        char[] arr = str.toCharArray();
        int last = 0;
        List<String> ans = new ArrayList<>();
        for(int i = 0; i < arr.length; ++i) {
            if(arr[i] == '}') {
                ans.add(str.substring(last, i+1));
                last = i+1;
            }
        }
        if(last != arr.length)
            ans.add(str.substring(last));
        return ans;
    }
}

// 最终参考题解的做法，使用递归解法，同样将函数分为三类，但是题解画了很清晰的流程图
class Solution {
    String expression;
    int idx;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.idx = 0;
        Set<String> ret = expr();
        return new ArrayList<String>(ret);
    }

    // item . letter | { expr }
    private Set<String> item() {
        Set<String> ret = new TreeSet<String>();
        if (expression.charAt(idx) == '{') {
            idx++;
            ret = expr();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(expression.charAt(idx));
            ret.add(sb.toString());
        }
        idx++;
        return ret;
    }

    // term . item | item term
    private Set<String> term() {
        // 初始化空集合，与之后的求解结果求笛卡尔积
        Set<String> ret = new TreeSet<String>() {{
            add("");
        }};
        // item 的开头是 { 或小写字母，只有符合时才继续匹配
        while (idx < expression.length() && (expression.charAt(idx) == '{' || Character.isLetter(expression.charAt(idx)))) {
            Set<String> sub = item();
            Set<String> tmp = new TreeSet<String>();
            for (String left : ret) {
                for (String right : sub) {
                    tmp.add(left + right);
                }
            }
            ret = tmp;
        }
        return ret;
    }

    // expr . term | term, expr
    private Set<String> expr() {
        Set<String> ret = new TreeSet<String>();
        while (true) {
            // 与 term() 求解结果求并集
            ret.addAll(term());
            // 如果匹配到逗号则继续，否则结束匹配
            if (idx < expression.length() && expression.charAt(idx) == ',') {
                idx++;
                continue;
            } else {
                break;
            }
        }
        return ret;
    }
}

// 作者：LeetCode-Solution
// 链接：https://leetcode.cn/problems/brace-expansion-ii/solution/hua-gua-hao-zhan-kai-ii-by-leetcode-solu-1s1y/
// 来源：力扣（LeetCode）
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
