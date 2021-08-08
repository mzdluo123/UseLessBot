package win.rainchan.uselessbot.controller

import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.firstIsInstanceOrNull
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.BotConfig


@Controller
class TQLController(private val config: BotConfig) : SimpleListenerHost() {

    private final val MSG = """Erdos相信上帝有一本记录所有数学中绝妙证明的书，上帝相信这本书在%name%手里
有一次费马惹怒了%name%，于是就有了费马最后定理
%name%从不会用光页边的空白
%name%的Erdos数是-1
如果%name%告诉你他在说谎，他就正在说真话
%name%从大到小列举了所有素数，就知道了素数有无穷多
%name%可以不重复地走遍柯尼斯堡的七座桥
%name%可以倒着写完圆周率的每一位
当数学家们使用通用语句——设n是一个正整数时，这是在请求%name%允许他们这样做
%name%小时候有一次要把正整数从1加到100，于是他用心算把所有正整数的和减去大于100的正整数的和
不是%name%发现了正态分布，而是自然规律在遵从%name%的意愿
一个数学家，一个物理学家，一个工程师走进一家酒吧，侍者说：“你好，%name%教授”
%name%可以走到莫比乌斯带的另一面
当%name%令一个正整数增加1时，那个正整数并没有增加，而是其他正整数减少了1
%name%同时给他自己和罗素剪头发
%name%不能理解什么是随机过程，因为他能预言随机数
有一次%name%证明了一个结论，但他不喜欢这个结论，于是%name%把它证伪了
有些级数是发散的，因为%name%觉得它们不值得加起来
问%name%一个定理是否正确可以作为一个定理的严谨证明
如果没有船，%name%可以把狼，羊，菜传送到河对岸
有一次%name%在森林里迷路了，于是他给这个森林添加了一些边把它变成了一棵树
只有%name%知道薛定谔的猫是死是活
通过故意遗漏证明最后的"证毕",%name%拯救了热带雨林
%name%可以剔掉奥卡姆剃刀
你刚证明了一个定理？%name%200年前就证明它了。
空集的定义是%name%不会证明的定理构成的集合
“我找不到反例”可以被视为一个定理的证明，如果它是%name%写下的
%name%把磁铁断为两块时，他得到两个单极磁铁
费马认为书页边缘写不下自己对费马大定理的证明，%name%能证明为什么这个证明这么长
上帝从不掷色子，除非%name%允许他赢一小会
平行线在%name%让它们相交的地方相交
当哥德尔听说%name%能证明一切命题时，他让%name%证明“存在一个命题%name%不能证明”——这就是量子态的来历
%name%可以看到自己头上帽子的颜色
%name%把无穷视为归纳证明的第一个非平凡情况
%name%可以用1种颜色染任何地图
%name%在求不定积分时不需要在最后加上一个常数
%name%无需站在任何人肩膀上就能比别人看的更远
%name%用克莱因瓶喝酒
%name%通过枚举法证伪了哥德尔不完备性定理/有一次%name%发现有一个定理自己不会证——这直接证明了哥德尔不完备定理
%name%有log(n)速度的排序算法
上帝创造了正整数，剩下的就是%name%的工作了
黎曼是%name%发表未公开成果时使用的名字
%name%不用任何公理就能证明一个定理
一个发现就是一个%name%的未公开结果
%name%使用无穷进制写数
%name%可以除以0
存在一个实数到被%name%证明了的定理的双射
%name%从不需要选择公理
%name%在200年前发明了64量子位计算机，但这让他的工作减速了
难题不会为%name%带来麻烦，%name%会为难题带来麻烦
%name%说过“数学是科学的皇后”，你猜谁是国王？
没有比65537大的费马素数，因为%name%发现费马将要发现什么了不起的事情，于是把它终结掉了
发散序列当看到%name%在旁边时会收敛
宇宙通过膨胀让自己的熵增加速度不超过%name%证明定理的速度
Erdos说他知道37个勾股定理的证明，%name%说他知道37个黎曼定理的证明，并留给黎曼做练习
希尔伯特23问题是他收集的%name%的手稿中留给读者做练习的那些问题
只有两件事物是无限的：人类的愚蠢和%name%的智慧，而且我对前者不太确定——爱因斯坦
%name%也发现了伽罗瓦理论，但他赢了那场决斗
%name%不能理解P与NP的问题，因为一切对他而言都是常数级别
%name%能心算干掉RSA公钥加密算法
%name%在实数集上使用数归
%name%从不证明任何定理——都是他的引理
不是%name%素数的素数会遭到戏弄
%name%可以做出正17边形——只用直尺
有一次%name%在脑子里构建了所有集合构成的集合
%name%证明了哥德巴赫猜想——通过检查所有情况
%name%可以把毛球捋平
世界上没有定理，只有%name%允许其正确的命题
%name%知道哪些图灵机会停机，因为它们运行前要得到%name%批准
在晚上，定理们围坐在篝火边给%name%讲故事
%name%本想证明三色定理，但他喜欢蓝色，所以放弃了
%name%当初面试Google时，被问到“如果P=NP能够推导出哪些结论”，%name%回答说：“P = 0或者N = 1”。而在面试官还没笑完的时候，%name%检查了一下Google的公钥，然后在黑板上写下了私钥。
编译器从不警告%name%，只有%name%警告编译器。
%name%的编码速度在2000年底提高了约40倍，因为他换了USB2.0的键盘。
%name%在提交代码前都会编译一遍，不过是为了检查编译器和链接器有没有出bug。
%name%有时候会调整他的工作环境和设备，不过这是为了保护他的键盘。
所有指针都指向%name%。
gcc -O4的功能是发送代码给%name%重写。
%name%有一次没有通过图灵测试，因为他正确说出了斐波那契数列的第203项的值，在一秒钟内。
真空中光速曾经是35英里每小时，直到%name%花了一个周末时间优化了一下物理法则。
%name%出生于1969年12月31日午后11点48分，他花了12分钟实现了他的第一个计时器。
%name%既不用Emacs也不用Vim，他直接输入代码到zcat，因为这样更快。
%name%发送以太网封包从不会发生冲突，因为其他封包都吓得逃回了网卡的缓冲区里。
因为对常数级的时间复杂度感到不满意，%name%发明了世界上第一个O(1/n)算法。
有一次%name%去旅行，期间Google的几个服务神秘地罢工了好几天。这是真事。
%name%被迫发明了异步API因为有一天他把一个函数优化到在调用前就返回结果了。
%name%首先写的是二进制代码，然后再写源代码作为文档。
%name%曾经写过一个O(n^2)算法，那是为了解决旅行商问题。
%name%有一次用一句printf实现了一个web服务器。其他工程师添加了数千行注释但依然无法完全解释清楚其工作原理。而这个程序就是今天Google首页的前端。
%name%可以下四子棋时用三步就击败你。
当你的代码出现未定义行为时，你会得到一个segmentation fault和一堆损坏的数据。当%name%的代码出现未定义行为时，一个独角兽会踏着彩虹从天而降并给每个人提供免费的冰激凌。
当%name%运行一个profiler时，循环们都会恐惧地自动展开。
%name%至今还在等待数学家们发现他隐藏在PI的小数点后数字里的笑话。
%name%的键盘只有两个键，1和0。
%name%失眠的时候，就Mapreduce羊。
%name%想听mp3的时候，他只需要把文件cat到/dev/dsp，然后在脑内解码。
Graham Bell当初发明出电话时，他看到有一个来自%name%的未接来电。
%name%的手表显示的是自1970年1月1日的秒数，并且从没慢过一秒。
%name%写程序是从’cat >/dev/mem’开始的。
有一天%name%出门时把笔记本错拿成了绘画板。在他回去拿笔记本的路上，他在绘图板上写了个俄罗斯方块打发时间。
%name%卡里只有8毛钱，本来想打个6毛的饭结果不小心按了9毛的，哪知机器忽然疯狂地喷出255两饭，被喷得满脸热饭的%name%大叫“烫烫烫烫烫烫。。。。”
%name%不洗澡是因为水力发电公司运行的是专有软件。
%name%的胡子是由括号构成的。
%name%从来不用洗澡；他只需要运行“make clean”。
%name%通过把一切都变得free而解决了旅行推销员问题。
%name%的左手和右手分别命名为“(”和“)”。
%name%用Emacs写出了Emacs的第一版。
有些人检查他们的电脑里是否有病毒。病毒检查他们的电脑里是否有%name%。
在一间普通的客厅里有1242件物体可以被%name%用来写一个操作系统，包括这房间本身。
当%name%还是个学数手指的小毛孩时，他总是从0开始数。
%name%不去kill一个进程，他只想看它是否胆敢继续运行。
当%name%指向（point at）一台Windows电脑时，它就会出现段错误。
%name%最初的话语是syscalls（系统调用）。
%name%之所以存在是因为他把自己编译成了生命体。
%name%是他自己在Emacs里用Lisp语言编写成的。
%name%能够通过Emacs的ssh客户端程序连接到任何大脑。
当%name%使用浮点数时，它们便没有舍入误差。
%name%不用维护代码。他注视着它们，直到它们带着敬仰改正自己的错误。
%name%不对开源项目作出贡献；开源项目对%name%作出贡献。
%name%的胡须里面不是下巴，而是另一撮胡须。如此递归直至无穷。
%name%曾经得过猪流感，但是该病毒很快被GPL污染并且同化了。
无论何时世界上有人写出一个“Hello, world”程序，%name%总以“Hello”回应。
%name%从不编译，他只要闭上眼睛，就能看见编译器优化时二进制位之间的能量流动被创造出来……
如果%name%有一个1GB的内存，你有一个1GB的内存，那么%name%拥有比你更多的内存。
当%name%执行ps -e时，你的名字会出现。
从来就没有软件开发过程这回事，只有被%name%允许存在的一些程序。
%name%的DNA中包含调试符号。尽管他从不需要它们。
%name%的医生能通过CVS采集他的血样。
对于%name%来说，多项式时间就是O(1)。
%name%将会使可口可乐在GPL协议下公布他们的配方。
%name%不需要用鼠标或键盘来操作计算机。他只要凝视着它，直到它完成想要的工作。
%name%就是图灵测试的解答。""".split("\n")


    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {
        if (config.checkGroup(group)) {
            if (message.contentToString().startsWith("tql")) {
                val target = message.firstIsInstanceOrNull<At>()
                if (target != null) {
                    group.sendMessage(MSG.random().replace("%name%", group[target.target]?.nameCardOrNick ?: "你"))
                } else {
                    val targetStr =  message.firstIsInstanceOrNull<PlainText>().toString().substring(3)
                    if (targetStr.isEmpty()){
                        group.sendMessage("谁太强了? 请输入 tql(at一个人或者是名字)")
                        return
                    }
                    group.sendMessage(MSG.random().replace("%name%", targetStr))
                }

            }
        }
    }
}