//package advisor;
//
//import java.lang.reflect.*;
//import java.util.*;
//
//class Packer {
//    public <T extends Bakery> void repackage(Box<? super Bakery> to, Box<? extends Bakery> from) {
//       T t = (T) from.get();
//
//        to.put(t);
//    }
//}
//
//// Don't change classes below
//class Box<T> {
//    private T item;
//
//    public void put(T item) {
//        this.item = item;
//    }
//
//    public T get() {
//        return this.item;
//    }
//}
//
//class Goods {
//}
//
//class Paper{}
//
//class Food extends Goods {
//}
//
//class Bakery extends Food {
//}
//
//class Cake extends Bakery {
//}
//
//class Pie extends Bakery {
//}
//
//class Tart extends Bakery {
//}
//
//class ListMultiplicator {
//
//    /**
//     Repeats original list content provided number of times
//     * @param list list to repeat
//     * @param n times to repeat, should be zero or greater
//     * @return
//     */
//    public static void multiply(List<?> list, int n) {
//        if (n == 0) {
//            ListIterator<?> iterator = list.listIterator();
//            while (iterator.hasNext()) {
//                iterator.next();
//                iterator.remove();
//            }
//        } else {
//            helper(list, n);
//        }
//    }
//
//    public static <T> void helper(List<T> list, int n) {
//        List<T> original = new ArrayList<>(list);
//        for (int i = 0; i < n - 1; i++) {
//            list.addAll(original);
//        }
//    }
//
//    public static void main(String[] args) {
//        List<Integer> ints = new ArrayList<>();
//        ints.add(1);
//        ints.add(2);
//        ints.add(3);
//        multiply(ints,1);
//        System.out.println(Arrays.toString(ints.toArray()));
//
//        System.out.println(check(defraud()));
//    }
//
//    public static List<Box<? extends Bakery>> defraud() {
//        List<Box<? extends Bakery>> boxes = new ArrayList<>();
//        Box paperBox = new Box();
//        paperBox.put(new Paper());
//        boxes.add(paperBox);
//        return boxes;
//    }
//
//    public static boolean check(List<Box<? extends Bakery>> boxes) {
//    /* Method signature guarantees that all illegal
//       calls will produce compile-time error... or not? */
//        return true;
//    }
//}
//
//class Multiplicator<T> {
//
//    private T[] array;
//
//    public static <T extends Copy<T>> Folder<T>[] multiply(Folder<T> folder, int arraySize) {
//        T originalItemInFolder = folder.get();
//        T copiedItem = originalItemInFolder.copy();
//
//        List<Folder<T>> folders = new ArrayList<>();
//        Folder<T> newFolder = new Folder<>();
//        newFolder.put(copiedItem);
//        folders.add(newFolder);
//
//        Folder<T>[] array = folders.toArray(new Folder[arraySize]);
//        return array;
//    }
//
//    public int getNumberOfFieldsClassDeclares(Class<?> clazz) {
//        Field[] declaredFields = clazz.getFields();
//        return declaredFields.length;
//    }
//
//    public static String getObjectClassName(Object object) {
//        return object.getClass().getName();
//    }
//
//    public Class getSuperClassByName(String name) throws ClassNotFoundException {
//        return Class.forName(name).getSuperclass();
//    }
//
//    public Class getSuperClassByInstance(Object object) {
//        return object.getClass().getSuperclass();
//    }
//
//    public static String findMethod(String methodName, String[] classNames) throws ClassNotFoundException {
//
//        for (String className : classNames) {
//            Class clazz = Class.forName(className);
//            Optional<Method> optionalMethod = Arrays.stream(clazz.getDeclaredMethods())
//                    .filter(method -> method.getName().equals(methodName))
//                    .findFirst();
//            if (optionalMethod.isPresent()) {
//                return clazz.getName();
//            }
//        }
//
//        return "";
//    }
//
//
//
//    public static void main(String[] args) throws ClassNotFoundException {
//        double[][][][] array = new double[1][1][12][1];
//        System.out.println(getObjectClassName(array));
//        System.out.println(findMethod("abs", new String[]{"java.lang.String", "java.lang.StringBuffer", "java.lang.Math"}));
//    }
//}
//
//// Don't change the code below
//interface Copy<T> {
//    T copy();
//}
//
//class Folder<T> {
//
//    private T item;
//
//    public void put(T item) {
//        this.item = item;
//    }
//
//    public T get() {
//        return this.item;
//    }
//}
//
//class TestClass{
//
//}
//
//class ListParameterInspector {
//    // Do not change the method
//    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        String methodName = scanner.next();
//
//        ListParameterInspector inspector = new ListParameterInspector();
//        inspector.printParameterType(new TestClass(), methodName);
//    }
//
//    public void printParameterType(TestClass obj, String methodName) throws Exception {
//        Method method = obj.getClass().getMethod(methodName);
//        ParameterizedType genericParameterizedType = (ParameterizedType) method.getGenericReturnType();
//        WildcardType wildCard = (WildcardType) genericParameterizedType.getActualTypeArguments()[0];
//
//        System.out.println(wildCard.getUpperBounds()[0].getTypeName());
//    }
//
//    public void printTypeVariablesCount(TestClass obj, String methodName) throws Exception {
//        Method method = obj.getClass().getMethod(methodName);
//        TypeVariable<Method>[] typeVar = method.getTypeParameters();
//
//
//        System.out.println(typeVar.length);
//    }
//}
//
//class QualityControl {
//
//    public static boolean check(List<Box<? extends Bakery>> boxes) {
//
//
//        for (Box<?> box : boxes) {
//            if (box.get() == null) {
//                return false;
//            }
//            if (box instanceof Box) {
//                if (!(box.get() instanceof Bakery)) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
////        return boxes.stream()
////                .noneMatch(box -> (box == null) || !(box instanceof Box) || !(((Box) box).get() instanceof Bakery));
//    }
//
//}