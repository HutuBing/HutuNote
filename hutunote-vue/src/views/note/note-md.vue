<template>
    <mavon-editor v-model="fileText"
                  :subfield="false"
                  :editable="false"
                  :defaultOpen="'preview'"
                  :style="{height: editorHeight}" />
</template>

<script>
    export default {
        name: "note-md",
        data() {
            return {
                editorHeight: "500px",
                fileText: '# JDK动态代理\n' +
                '\n' +
                '### 实现InvocationHandler,构造器传入代理目标\n' +
                '```\n' +
                'public class MyHandler implements InvocationHandler{\n' +
                '    private Object target;\n' +
                '    \n' +
                '    MyHandler(Object target){\n' +
                '        this.target = target;\n' +
                '    }\n' +
                '    \n' +
                '    public Object invoke(Object proxy, Method method, Object[] args){\n' +
                '        doBefore();\n' +
                '        Object r = method.invoke(target, args);\n' +
                '        doAfter();\n' +
                '        return r;\n' +
                '    }\n' +
                '}\n' +
                '```\n' +
                '### 调用Proxy的newProxyInstance方法，传入自定义Handler\n' +
                '\n' +
                '```\n' +
                'public class Proxy{\n' +
                '    static newProxyInstance(ClassLoader classLoader,Class<?>[]interfaces, InvocationHandler handler){\n' +
                '        ...\n' +
                '    }\n' +
                '}\n' +
                '```\n' +
                '### 工厂类示例\n' +
                '```\n' +
                'public class MyJdkProxyFactory {\n' +
                '    public Object getProxy(Object target){\n' +
                '        return Proxy.newInstance(\n' +
                '            target.getClass().getClassLoader(),\n' +
                '            target.getClass().getInterfaces(),\n' +
                '            new MyHandler(target)\n' +
                '        );\n' +
                '    }\n' +
                '}\n' +
                '```\n' +
                '# CgLib动态代理\n' +
                '### 实现MethodInterceptor的intercept方法\n' +
                '```\n' +
                'public class MyInterceptor implements MethodInterceptor {\n' +
                '    \n' +
                '    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy){\n' +
                '        doBefore();\n' +
                '        //Cglib原理是创建目标对象子类，因此调用invokeSuper\n' +
                '        Object r = proxy.invokeSuper(target, args);\n' +
                '        doAfter();\n' +
                '        return r;\n' +
                '    }\n' +
                '    \n' +
                '}\n' +
                '```\n' +
                '### 使用Enhancer创建代理对象\n' +
                '```\n' +
                'public class MyCglibProxyFactory{\n' +
                '    public Object getProxy(Class target){\n' +
                '        Enhancer enhancer = new Enhancer();\n' +
                '        enhancer.setClassLoader(target.getClassLoader());\n' +
                '        enhancer.setSuperClass(target);\n' +
                '        enhancer.setCallable(new MyInterceptor());\n' +
                '        return enhancer.create();\n' +
                '    }\n' +
                '}\n' +
                '```\n' +
                '# 两者区别\n' +
                '- JDK动态代理只能代理实现接口的类，代理对象实现了目标类接口，将目标类作为成员变量\n' +
                '- Cglib可以代理未实现接口的类，代理类继承目标类，因此不能代理final修饰的类和方法\n' +
                '- JDK代理性能优于Cglib'
            }
        },
        created() {
            this.initEditorHeight()
        },
        methods: {
            initEditorHeight() {
                this.editorHeight = document.documentElement.clientHeight + "px"
            }
        }
    }
</script>

<style scoped>

</style>