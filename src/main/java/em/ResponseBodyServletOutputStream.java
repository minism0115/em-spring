package em;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseBodyServletOutputStream extends ServletOutputStream {

    private final DataOutputStream outputStream;

    public ResponseBodyServletOutputStream(OutputStream output) {
        this.outputStream = new DataOutputStream(output);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }

    @Override
    public void write(int b) throws IOException {

    }
}
