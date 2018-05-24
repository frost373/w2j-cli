package top.thinkin.wjcli.util;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 数组工具类
 * 
 * @author Looly
 *
 */
public class ArrayUtil {

	/**
	 * 对象是否为数组对象
	 *
	 * @param obj 对象
	 * @return 是否为数组对象，如果为{@code null} 返回false
	 */
	public static boolean isArray(Object obj) {
		if (null == obj) {
//			throw new NullPointerException("Object check for isArray is null");
			return false;
		}
		return obj.getClass().isArray();
	}

	/** 数组中元素未找到的下标，值为-1 */
	// ---------------------------------------------------------------------- isEmpty
	/**
	 * 数组是否为空
	 * 
	 * @param <T> 数组元素类型
	 * @param array 数组
	 * @return 是否为空
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isEmpty(final T... array) {
		return array == null || array.length == 0;
	}

	// ---------------------------------------------------------------------- isNotEmpty
	/**
	 * 数组是否为非空
	 * 
	 * @param <T> 数组元素类型
	 * @param array 数组
	 * @return 是否为非空
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isNotEmpty(final T... array) {
		return (array != null && array.length != 0);
	}





	/**
	 * 新建一个空数组
	 * 
	 * @param <T> 数组元素类型
	 * @param componentType 元素类型
	 * @param newSize 大小
	 * @return 空数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> componentType, int newSize) {
		return (T[]) Array.newInstance(componentType, newSize);
	}
	


	/**
	 * 将新元素添加到已有数组中<br>
	 * 添加新元素会生成一个新的数组，不影响原数组
	 * 
	 * @param <T> 数组元素类型
	 * @param buffer 已有数组
	 * @param newElements 新元素
	 * @return 新数组
	 */
	public static <T> T[] append(T[] buffer, T... newElements) {
		if (isEmpty(newElements)) {
			return buffer;
		}

		T[] t = resize(buffer, buffer.length + newElements.length);
		System.arraycopy(newElements, 0, t, buffer.length, newElements.length);
		return t;
	}

	/**
	 * 生成一个新的重新设置大小的数组<br>
	 * 调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
	 * 
	 * @param <T> 数组元素类型
	 * @param buffer 原数组
	 * @param newSize 新的数组大小
	 * @param componentType 数组元素类型
	 * @return 调整后的新数组
	 */
	public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
		T[] newArray = newArray(componentType, newSize);
		if (isNotEmpty(buffer)) {
			System.arraycopy(buffer, 0, newArray, 0, Math.min(buffer.length, newSize));
		}
		return newArray;
	}

	/**
	 * 生成一个新的重新设置大小的数组<br>
	 * 新数组的类型为原数组的类型，调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
	 * 
	 * @param <T> 数组元素类型
	 * @param buffer 原数组
	 * @param newSize 新的数组大小
	 * @return 调整后的新数组
	 */
	public static <T> T[] resize(T[] buffer, int newSize) {
		return resize(buffer, newSize, buffer.getClass().getComponentType());
	}



	/**
	 * 数组或集合转String
	 *
	 * @param obj 集合或数组对象
	 * @return 数组字符串，与集合转字符串格式相同
	 */
	public static String toString(Object obj){
		if (null == obj) {
			return null;
		}
		if (ArrayUtil.isArray(obj)) {
			try {
				return Arrays.deepToString((Object[]) obj);
			} catch (Exception e) {
				final String className = obj.getClass().getComponentType().getName();
				if ("long".equals(className)) {
					return Arrays.toString((long[]) obj);
				} else if ("int".equals(className)) {
					return Arrays.toString((int[]) obj);
				} else if ("short".equals(className)) {
					return Arrays.toString((short[]) obj);
				} else if ("char".equals(className)) {
					return Arrays.toString((char[]) obj);
				} else if ("byte".equals(className)) {
					return Arrays.toString((byte[]) obj);
				} else if ("boolean".equals(className)) {
					return Arrays.toString((boolean[]) obj);
				} else if ("float".equals(className)) {
					return Arrays.toString((float[]) obj);
				} else if ("double".equals(className)) {
					return Arrays.toString((double[]) obj);
				} else {
					throw new RuntimeException(e);
				}
			}
		}
		return obj.toString();
	}

	/**
	 * 包装数组对象
	 *
	 * @param obj 对象，可以是对象数组或者基本类型数组
	 * @return 包装类型数组或对象数组
	 * @throws RuntimeException 对象为非数组
	 */
	public static Object[] wrap(Object obj) {
		if (isArray(obj)) {
			try {
				return (Object[]) obj;
			} catch (Exception e) {
				final String className = obj.getClass().getComponentType().getName();
				if ("long".equals(className)) {
					return wrap((long[]) obj);
				} else if ("int".equals(className)) {
					return wrap((int[]) obj);
				} else if ("short".equals(className)) {
					return wrap((short[]) obj);
				} else if ("char".equals(className)) {
					return wrap((char[]) obj);
				} else if ("byte".equals(className)) {
					return wrap((byte[]) obj);
				} else if ("boolean".equals(className)) {
					return wrap((boolean[]) obj);
				} else if ("float".equals(className)) {
					return wrap((float[]) obj);
				} else if ("double".equals(className)) {
					return wrap((double[]) obj);
				} else {
					throw new RuntimeException(e);
				}
			}
		}
		throw new RuntimeException(StrUtil.format("[{}] is not Array!", obj.getClass()));
	}





	/**
	 * 以 conjunction 为分隔符将数组转换为字符串
	 *
	 * @param <T> 被处理的集合
	 * @param array 数组
	 * @param conjunction 分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(T[] array, CharSequence conjunction) {
		if (null == array) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : array) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}
			if (ArrayUtil.isArray(item)) {
				sb.append(join(ArrayUtil.wrap(item), conjunction));
			} else if (item instanceof Iterable<?>) {
				sb.append(join((Iterable<?>) item, conjunction));
			} else if (item instanceof Iterator<?>) {
				sb.append(join((Iterator<?>) item, conjunction));
			} else {
				sb.append(item);
			}
		}
		return sb.toString();
	}

	/**
	 * 以 conjunction 为分隔符将集合转换为字符串
	 *
	 * @param <T> 集合元素类型
	 * @param iterable {@link Iterable}
	 * @param conjunction 分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(Iterable<T> iterable, CharSequence conjunction) {
		if (null == iterable) {
			return null;
		}
		return join(iterable.iterator(), conjunction);
	}

	/**
	 * 以 conjunction 为分隔符将集合转换为字符串<br>
	 * 如果集合元素为数组、{@link Iterable}或{@link Iterator}，则递归组合其为字符串
	 *
	 * @param <T> 集合元素类型
	 * @param iterator 集合
	 * @param conjunction 分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(Iterator<T> iterator, CharSequence conjunction) {
		if (null == iterator) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		T item;
		while (iterator.hasNext()) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}

			item = iterator.next();
			if (ArrayUtil.isArray(item)) {
				sb.append(ArrayUtil.join(ArrayUtil.wrap(item), conjunction));
			} else if (item instanceof Iterable<?>) {
				sb.append(join((Iterable<?>) item, conjunction));
			} else if (item instanceof Iterator<?>) {
				sb.append(join((Iterator<?>) item, conjunction));
			} else {
				sb.append(item);
			}
		}
		return sb.toString();
	}

}
