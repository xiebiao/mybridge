package mybridge2.packet;

import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能简述> Command 命令执行结果描述类。 <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-6-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MysqlResultSetPacket extends Packet {
	private boolean isResultSet = false;

	private ResultSetHeaderPacket resulthead;

	private List<FieldPacket> fieldPackets;

	private List<RowDataPacket> rowList;

	private EOFPacket eof1;

	private EOFPacket eof2;

	byte paketId;

	/**
	 * <默认构造函数> 如果是error 传递null
	 */
	public MysqlResultSetPacket(String[][] resultList) {

		eof1 = new EOFPacket();
		eof2 = new EOFPacket();
		resulthead = new ResultSetHeaderPacket();
		fieldPackets = new ArrayList<FieldPacket>();
		rowList = new ArrayList<RowDataPacket>();
		paketId = 1;
		initResultSetByArray(resultList);
	}

	// public void write2Buffer(PacketBuffer buffer)
	// throws UnsupportedEncodingException
	// {
	// byte paketId = 1;
	//
	// if (!isResultSet)
	// {
	// ErrorPacket errorPacket = new ErrorPacket();
	// errorPacket.packetId = paketId;
	// errorPacket.write2Buffer(buffer);
	// return;
	// }
	//
	// if (fieldPackets == null || fieldPackets.size() < 1)
	// {
	// ErrorPacket errorPacket = new ErrorPacket();
	// errorPacket.packetId = paketId;
	// errorPacket.write2Buffer(buffer);
	// return;
	// }
	//
	// resulthead.write2Buffer(buffer);
	//
	// // write fields bytes
	// for (FieldPacket field : fieldPackets)
	// {
	// field.write2Buffer(buffer);
	// }
	// // write eof bytes
	// eof1.write2Buffer(buffer);
	//
	// // write rowdata
	// if (rowList != null && rowList.size() > 0)
	// {
	// for (RowDataPacket row : rowList)
	// {
	// row.write2Buffer(buffer);
	// }
	//
	// }
	//
	// // write eof bytes
	// eof2.write2Buffer(buffer);
	// }

	private void initResultSetByArray(String[][] resultList) {

		fieldPackets.clear();
		rowList.clear();

		if (resultList == null || resultList.length < 1) {
			return;
		}
		if (resultList[0].length < 1) {
			return;
		}

		/**
		 * 初始化 ResultSetHeaderPacket
		 */
		resulthead.packetId = paketId++;
		resulthead.setColumns(resultList[0].length);

		/**
		 * 初始化FieldPacket
		 */

		FieldPacket field;
		for (int i = 0; i < resultList[0].length; i++) {
			field = new FieldPacket();
			field.setName(resultList[0][i]);
			field.setOrgName(resultList[0][i]);
			field.packetId = paketId++;
			fieldPackets.add(field);
		}

		/**
		 * 初始化EOFPacket
		 */
		eof1.packetId = paketId++;

		/**
		 * 初始化RowDataPacket
		 */

		RowDataPacket rowDataPacket;
		for (int i = 1; i < resultList.length; i++) {
			if (resultList[i].length < 1) {
				continue;
			}

			rowDataPacket = new RowDataPacket();
			for (int j = 0; j < resultList[i].length; j++) {
				rowDataPacket.addColumn(resultList[i][j]);
			}
			rowDataPacket.packetId = paketId++;
			rowList.add(rowDataPacket);
		}

		// write eof bytes
		eof2.packetId = paketId++;

		isResultSet = true;
	}

	public List<Packet> getPackets() {

		List<Packet> list = new ArrayList<Packet>();
		if (!isResultSet) {
			ErrorPacket errorPacket = new ErrorPacket();
			errorPacket.packetId = paketId;
			list.add(errorPacket);
			return list;
		} else {

			list.add(resulthead);
			list.addAll(fieldPackets);
			list.add(eof1);
			list.addAll(rowList);
			list.add(eof2);
		}
		return list;
	}

}
