package frontend;

import java.io.File;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileSystemTreeModel implements TreeModel {

	private final TreeFileObject root;

	public FileSystemTreeModel(File root) {
		this.root = new TreeFileObject(root);
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		File parentFolder = ((TreeFileObject) parent).getFile();
		return new TreeFileObject(parentFolder.listFiles()[index]);
	}

	@Override
	public int getChildCount(Object parent) {
		File parentFile = ((TreeFileObject) parent).getFile();
		if (parentFile != null && parentFile.isDirectory()) {
			File[] files = parentFile.listFiles();
			// Sometimes files is null. Fuck swing.
			if (files != null) {
				return files.length;
			}
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		File nodeFile = ((TreeFileObject) node).getFile();
		return nodeFile.isFile();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		File folder = ((TreeFileObject) parent).getFile();
		File file = ((TreeFileObject) child).getFile();
		File[] contents = folder.listFiles();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].equals(file)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {

	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {

	}

	public static class TreeFileObject {
		private final File file;

		public TreeFileObject(File file) {
			this.file = file;
		}

		@Override
		public String toString() {
			return getFile().getName();
		}

		public File getFile() {
			return file;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((file == null) ? 0 : file.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TreeFileObject other = (TreeFileObject) obj;
			if (file == null) {
				if (other.file != null)
					return false;
			} else if (!file.equals(other.file))
				return false;
			return true;
		}

	}

}
