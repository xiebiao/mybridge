package com.github.mybridge.query;

public class ThreadQuery extends Base {

	private class Query implements Runnable {
		private int count;

		public Query(int count) {
			this.count = count;
		}

		@Override
		public void run() {
			try {
				ThreadQuery.this.query(count);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void go() {
		int count = 100;
		while (count-- > 0) {
			new Thread(new Query(count)).start();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadQuery query = new ThreadQuery();
		query.go();

	}

}
