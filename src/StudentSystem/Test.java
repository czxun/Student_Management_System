package StudentSystem;

import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        System.out.println(getCode());

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
