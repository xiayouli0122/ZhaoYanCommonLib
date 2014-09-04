package com.zhaoyan.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Log;

public class ArrayUtil {
	private static final String TAG = "ArrayUtil";

	/**
	 * Join two arrays to a new array.
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static byte[] join(byte[] a1, byte[] a2) {
		byte[] result = new byte[a1.length + a2.length];
		System.arraycopy(a1, 0, result, 0, a1.length);
		System.arraycopy(a2, 0, result, a1.length, a2.length);
		return result;
	}

	/**
	 * Join two or more arrays a new array.
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] join(byte[]... a) {
		if (a == null) {
			return null;
		}
		int count = a.length;
		// one array.
		if (count == 1) {
			return a[0];
		}
		// two or more arrays.
		int totalLength = 0;
		for (int i = 0; i < count; i++) {
			totalLength += a[i].length;
		}
		byte[] result = new byte[totalLength];
		int p = 0;
		for (int i = 0; i < a.length; i++) {
			System.arraycopy(a[i], 0, result, p, a[i].length);
			p += a[i].length;
		}
		return result;
	}

	/**
	 * convert int to byte array.
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] int2ByteArray(int i) {
		byte[] result = new byte[4];
		// first 4 bit
		result[0] = (byte) ((i >> 24) & 0xff);
		// second 4 bit
		result[1] = (byte) ((i >> 16) & 0xff);
		// third 4 bit
		result[2] = (byte) ((i >> 8) & 0xff);
		// fourth 4 bit.
		result[3] = (byte) (i & 0xff);
		return result;
	}

	/**
	 * convert byte array to int.
	 * 
	 * @param array
	 * @return
	 */
	public static int byteArray2Int(byte[] array) {
		int reuslt = 0;
		byte loop;
		for (int i = 0; i < 4; i++) {
			loop = array[i];
			int offset = array.length - i - 1;
			reuslt += (loop & 0xFF) << (8 * offset);
		}
		return reuslt;
	}

	/**
	 * Convert Serializable object to byte array.
	 * 
	 * @param o
	 * @return
	 */
	public static byte[] objectToByteArray(Serializable o) {
		byte[] result = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			objectOutputStream.writeObject(o);

			result = byteArrayOutputStream.toByteArray();

			byteArrayOutputStream.close();
			objectOutputStream.close();
		} catch (Exception e) {
			Log.e(TAG, "objectToByteArray() error: " + e);
		}
		return result;
	}

	/**
	 * Convert Serializable object to byte array.
	 * 
	 * @param o
	 * @return
	 */
	public static byte[] objectToByteArray(Serializable... o) {
		byte[] result = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			for (int i = 0; i < o.length; i++) {
				objectOutputStream.writeObject(o[i]);
			}
			result = byteArrayOutputStream.toByteArray();

			byteArrayOutputStream.close();
			objectOutputStream.close();
		} catch (Exception e) {
			Log.e(TAG, "objectToByteArray() error: " + e);
		}
		return result;
	}

	/**
	 * Convert byte array to Serializable object.
	 * 
	 * @param data
	 * @return
	 */
	public static Object byteArrayToObject(byte[] data) {
		Object result = null;
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					data);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			result = objectInputStream.readObject();

			byteArrayInputStream.close();
			objectInputStream.close();
		} catch (Exception e) {
			Log.e(TAG, "byteArrayToObject() error: " + e);
		}
		return result;
	}

}
