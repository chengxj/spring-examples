package foo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.Map;

@Aspect
public class AspceJAdvice {
    private static final AspceJAdvice INSTANCE = new AspceJAdvice();
    private DataStore dataStore;

    public static AspceJAdvice aspectOf() {
        return INSTANCE;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Pointcut("execution(public * find(..))")
    private void aspectjMethod(){

    }

    @Before("aspectjMethod()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("beforeAdvice() is running");
        System.out.println("\thijacked method: " + joinPoint.getSignature().getName());
    }

    @After("aspectjMethod()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("afterAdvice() is running");
        System.out.println("\tHijacked method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "aspectjMethod()", returning = "retVal")
    public void afterReturningAdvice(JoinPoint joinPoint, Map<String, String> retVal) {
        System.out.println("afterReturningAdvice() is running");
        System.out.println("\tHijacked method: " + joinPoint.getSignature().getName());
        System.out.println("\tReturned value: " + retVal);
        retVal.put("Country", "China");
    }
}
