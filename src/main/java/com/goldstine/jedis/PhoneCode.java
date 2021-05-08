package com.goldstine.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {

    public static void main(String[] args) {
//        String code = getCode();

//        System.out.println(code);
//        verifyCode("15797899690");
        getRedisCode("15797899690","829014");
    }


    //首先生成6位的验证码
    public static String getCode(){
        Random random = new Random();

        String code="";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code=code+rand;
        }
        return code;
    }
    //每一个手机号每一天只能接收3次验证码，将验证码存入redis，设置验证码的过期时间
    public static void verifyCode(String phone){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        //拼接
        //拼接phone发送次数 key
        String countKey="VerifyCode"+phone+":count";
        String codeKey="VerifyCode"+phone+":code";
        //每个手机每天只能发送三次
        String count = jedis.get(countKey);

        if(count==null){
            //说明还没有发送，设置为1
            jedis.setex(countKey,24*60*60,"1");

        } else if (Integer.parseInt(count) <= 2) {
            jedis.incrBy(countKey,1);

        }else if(Integer.parseInt(count)>2){
            System.out.println("今天的验证次数超过3次");
            jedis.close();
        }
        //发送的验证码放到redis里面
        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
        jedis.close();

    }

    //校验验证码
    public static void getRedisCode(String phone,String code){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        String codeKey="VerifyCode"+phone+":code";
        String redisCode=jedis.get(codeKey);
        if (redisCode.equals(code)) {
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
        jedis.close();
    }

}
