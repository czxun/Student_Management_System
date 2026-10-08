package StudentSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class App {
    public static void main(String[] args) {

        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String choose = null;
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作: 1登录 2注册 3忘记密码");
            choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("退出");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc =new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username=sc.next();
        boolean flag=contains(list,username);
        if(!flag){
            System.out.println("当前用户"+username+"未注册,请先注册");
            return;
        }

        //键盘录入身份证号码和手机号码
        System.out.println("请输入身份证号码");
        String personID=sc.next();
        System.out.println("请输入手机号码");
        String phoneNumber=sc.next();

        //比较用户对象的手机号码和身份证号码是否相同
        //需要把用户对象先获取出来
        int index=findIndex(list,username);
        User user=list.get(index);

        if(!(user.getPersonID().equalsIgnoreCase(personID)&&user.getPhoneNumber().equals(phoneNumber))){
            System.out.println("身份证号码或手机号码错误,无法修改密码");
            return;
        }

        //修改密码
        String password;
        while (true) {
            System.out.println("请输入新的密码");
            password= sc.next();
            System.out.println("请再次输入新的密码");
            String againPassword=sc.next();
            if(password.equals(againPassword)){
                System.out.println("两次密码输入一致");
                break;
            }else{
                System.out.println("两次密码输入不一致,请重新输入");
                continue;
            }
        }

        user.setPassword(password);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    private static void register(ArrayList<User> list) {
        //用户名,密码,身份证号码,手机号码录入
        //将用户对象加入到集合中
        //1.键盘录入用户名
        String username;
        String password;
        String personID;
        String phoneNumber;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名");
            username = sc.next();
            //先验证格式是否正确,再验证是否唯一

            boolean flag1 = checkUsername(username);
            //用户名唯一
            if (!flag1) {
                System.out.println("用户名格式不满足条件,需要重新输入");
                continue;
            }
            //用户名唯一
            boolean flag2 = contains(list, username);
            if (flag2) {
                //用户名已存在
                System.out.println("用户名" + username + "已存在,请重新输入");
            } else {
                //当前用户名可用
                System.out.println("用户名" + username + "可用");
                break;
            }
        }

        //键盘录入密码
        //密码键盘输入两次,一致方可注册
        while (true) {
            System.out.println("请输入注册密码");
            password = sc.next();
            System.out.println("请再次输入注册密码");
            String againPassword = sc.next();
            if (!password.equals(againPassword)) {
                System.out.println("密码不一致,请重新输入");
                continue;
            }else
            {
                System.out.println("两次密码一致,继续录入");
                break;
            }
        }

        //3.键盘录入身份证号码
        //长度18位,不能以0开头,前17位必须是数字,最后一位可以是数字,也可以是X或x
        while (true) {
        System.out.println("请输入身份证号码");
        personID=sc.next();
        boolean flag=checkPersonID(personID);

            if(flag){
                System.out.println("身份证号码满足要求");
                break;
            }else{
                System.out.println("身份证号码格式有误,请重新输入");
                continue;
            }
        }

        //4.键盘录入手机号码
        /*长度为11位,不要以零开头,必须都是数字*/
        while (true) {
            System.out.println("请输入手机号码");
            phoneNumber= sc.next();
            boolean flag=checkPhoneNumber(phoneNumber);
            if(flag){
                System.out.println("手机号码格式正确");
                break;
            }else{
                System.out.println("手机号码格式错误");
                continue;
            }
        }

        //用户名,密码,身份证,手机号码放入用户中
        User u=new User(username,password,personID,phoneNumber);
        list.add(u);
        System.out.println("注册成功");
        printList(list);
    }

    private static void printList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            System.out.println(user.getUsername()+", "+user.getPassword()+", "+user.getPersonID()+", "+user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        if(phoneNumber.length()!=11){
            return false;
        }
        boolean flag=phoneNumber.startsWith("0");
        if(flag){
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c=phoneNumber.charAt(i);
            if(!(c>='0'&&c<='9'))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean checkPersonID(String personID) {
        if(personID.length()!=18)
        {
            return false;
        }
        boolean flag=personID.startsWith("0");
        if(flag){
            return false;
        }

        for (int i = 0; i < personID.length()-1; i++) {
            char c=personID.charAt(i);
            if(!(c>='0'&&c<='9')){
                return false;
            }
        }

        char endChar=personID.charAt(personID.length()-1);
        if((endChar>='0'&&endChar<='9')||(endChar=='x')||(endChar=='X'))
        {
            return true;
        }else {
            return false;
        }
    }

    private static boolean contains(ArrayList<User> list, String username) {
        //循环遍历集合得到每一个用户对象
        //拿着用户对象中的用户名进行比较
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            String rightUsername = user.getUsername();
            if (rightUsername.equals(username)) {
                return true;
            }
        }
        //循环结束,返回false
        return false;
    }

    private static boolean checkUsername(String username) {
        //用户名长度在3-15位之间
        int len = username.length();
        if (len < 3 || len > 15) {
            return false;
        }
        //继续校验,6必须是字母加数字的组合,并且不能是纯数字
        for (int i = 0; i < username.length(); i++) {
            //i索引
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }

        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            //i索引
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    private static void login(ArrayList<User> list) {
        Scanner sc=new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入用户名");
            String username=sc.next();
            boolean flag=contains(list,username);
            if(!flag){
                System.out.println("用户名"+username+"未注册,请先注册再登录");
            }

            System.out.println("请输入密码");
            String password= sc.next();

            while (true) {
                String rightCode=getCode();
                System.out.println("当前正确的验证码为:"+rightCode);
                System.out.println("请输入验证码");
                String code=sc.next();
                if(code.equalsIgnoreCase(rightCode)){
                    System.out.println("验证码正确");
                    break;
                }else{
                    System.out.println("验证码错误");
                    continue;
                }
            }

            //验证用户名和密码是否正确
            //集合中是否包含用户名和密码
            //定义方法验证用户名和密码是否正确
            User useInfo=new User(username,password,null,null);
            boolean result=checkPUserIndo(list,useInfo);
            if(result){
                System.out.println("登录成功");
                StudentSystem ss=new StudentSystem();
                ss.startStudentSystem();
                break;
            }else{
                System.out.println("登录失败,用户名或密码错误");
                if(i==2){
                    System.out.println("该用户"+username+"已被锁定,请联系管理员");
                    return;
                }else {
                    System.out.println("用户名或密码错误,还剩下"+(2-i)+"次机会");
                }
            }
        }
        }


    private static boolean checkPUserIndo(ArrayList<User> list,User useInfo) {
        //遍历集合,判断用户是否存在,如果存在登录成功,如果不存在登录失败
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getUsername().equals(useInfo.getUsername())&&user.getPassword().equals(useInfo.getPassword())){
                return true;
            }
        }
        return false;
    }

    private static String getCode(){
        ArrayList<Character> list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }

        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            int index=r.nextInt(list.size());
            char c=list.get(index);
            sb.append(c);
        }

        int number=r.nextInt(10);
        sb.append(number);

        //修改字符串中的内容
        //先将字符串变成字符数组,在数组中进行修改,再创建一个新的字符串
        char[] arr=sb.toString().toCharArray();
        //拿着最后一个索引,跟随机索引进行交换
        int randomIndex=r.nextInt(arr.length);
        //最大索引指向元素与随机索引指向元素交换
        char temp=arr[randomIndex];
        arr[randomIndex]=arr[arr.length-1];
        arr[arr.length-1]=temp;

        return new String(arr);
    }
}

