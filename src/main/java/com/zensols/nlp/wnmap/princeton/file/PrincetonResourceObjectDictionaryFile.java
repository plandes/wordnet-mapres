package com.zensols.nlp.wnmap.princeton.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.JWNLIOException;
import net.sf.extjwnl.JWNLRuntimeException;
import net.sf.extjwnl.data.DictionaryElement;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.dictionary.file.DictionaryFileFactory;
import net.sf.extjwnl.dictionary.file.DictionaryFileType;
import net.sf.extjwnl.dictionary.file.ObjectDictionaryFile;
import net.sf.extjwnl.princeton.file.AbstractPrincetonRandomAccessDictionaryFile;
import net.sf.extjwnl.princeton.file.PrincetonResourceDictionaryFile;
import net.sf.extjwnl.util.ByteArrayCharSequence;
import net.sf.extjwnl.util.CharBufferCharSequence;
import net.sf.extjwnl.util.PointedCharSequence;
import net.sf.extjwnl.util.factory.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrincetonResourceObjectDictionaryFile extends AbstractPrincetonRandomAccessDictionaryFile
    implements ObjectDictionaryFile, DictionaryFileFactory<PrincetonResourceObjectDictionaryFile> {

    private static final Logger log = LoggerFactory.getLogger(PrincetonResourceObjectDictionaryFile.class);

    private byte[] buffer;
    private int firstLineOffset;
    private final CharsetDecoder decoder;

    /**
     * Factory constructor.
     *
     * @param dictionary dictionary
     * @param params     params
     */
    public PrincetonResourceObjectDictionaryFile(Dictionary dictionary, Map<String, Param> params) {
        super(dictionary, params);
        this.decoder = null;
    }

    /**
     * Instance constructor.
     *
     * @param dictionary dictionary
     * @param path       file path
     * @param pos        part of speech
     * @param fileType   file type
     * @param params     params
     */
    public PrincetonResourceObjectDictionaryFile(Dictionary dictionary, String path, POS pos, DictionaryFileType fileType, Map<String, Param> params) {
        super(dictionary, path, pos, fileType, params);
        this.firstLineOffset = -1;
        this.buffer = null;
        if (null != encoding) {
            Charset charset = Charset.forName(encoding);
            decoder = charset.newDecoder();
        } else {
            decoder = null;
        }
    }

    @Override
    public PrincetonResourceObjectDictionaryFile newInstance(Dictionary dictionary, String path, POS pos, DictionaryFileType fileType) {
        return new PrincetonResourceObjectDictionaryFile(dictionary, path, pos, fileType, params);
    }

    public long length() throws JWNLException {
        return buffer.length;
    }

    @Override
    public long getNextLineOffset(long offset) throws JWNLException {
        if (!isOpen()) {
            throw new JWNLException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_001"));
        }

        int loffset = (int) offset;

        if (loffset >= buffer.length || loffset < 0) {
            return -1;
        }

        int i = loffset;
        while (i < buffer.length && '\n' != buffer[i]) {
            i++;
        }
        // we've read the line

        long result = i + 1;
        if (result >= buffer.length) {
            result = -1;
        }

        return result;
    }

    @Override
    public PointedCharSequence readLine(long offset) throws JWNLException {
        if (!isOpen()) {
            throw new JWNLException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_001"));
        }

        // long files limitation due to limitation in ByteBuffer.
        int loffset = (int) offset;

        if (loffset >= buffer.length || loffset < 0) {
            return null;
        }

        int i = loffset;
        while (i < buffer.length && '\n' != buffer[i]) {
            i++;
        }

        // resulting line ends at i (eol or eof)
        final PointedCharSequence result;

        if (null == encoding) {
            result = new ByteArrayCharSequence(buffer, loffset, i, i);
        } else {
            final ByteBuffer bb = ByteBuffer.wrap(buffer, loffset, i - loffset);

            try {
                synchronized (this) {
                    CharBuffer cb = decoder.decode(bb);
                    result = new CharBufferCharSequence(cb, i);
                }
            } catch (CharacterCodingException e) {
                throw new JWNLIOException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_003",
                        new Object[]{getFilename(), loffset}), e);
            }
        }
        return result;
    }

    @Override
    public PointedCharSequence readWord(long offset) throws JWNLException {
        if (!isOpen()) {
            throw new JWNLException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_001"));
        }

        // long files limitation due to limitation in ByteBuffer.
        int loffset = (int) offset;

        if (loffset >= buffer.length || loffset < 0) {
            return null;
        }

        int i = loffset;
        while (i < buffer.length && ' ' != buffer[i] && '\n' != buffer[i]) {
            i++;
        }

        // resulting word ends at i (space, eol or eof)
        final PointedCharSequence result;

        if (null == encoding) {
            result = new ByteArrayCharSequence(buffer, loffset, i, i);
        } else {
            final ByteBuffer bb = ByteBuffer.wrap(buffer, loffset, i - loffset);

            try {
                synchronized (this) {
                    CharBuffer cb = decoder.decode(bb);
                    result = new CharBufferCharSequence(cb, i);
                }
            } catch (CharacterCodingException e) {
                throw new JWNLIOException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_003",
                        new Object[]{getFilename(), loffset}), e);
            }
        }
        return result;
    }

    @Override
    public long getFirstLineOffset() throws JWNLException {
        // fixed DCL idiom: http://en.wikipedia.org/wiki/Double-checked_locking
        if (-1 == firstLineOffset) {
            synchronized (this) {
                if (-1 == firstLineOffset) {
                    if (!isOpen()) {
                        throw new JWNLException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_001"));
                    }

                    int i = 0;
                    boolean eol = true;
                    while (i < buffer.length) {
                        if (eol && ' ' != buffer[i]) {
                            break;
                        }
                        eol = '\n' == buffer[i];
                        i++;
                    }

                    firstLineOffset = i;
                }
            }
        }

        return firstLineOffset;
    }

    @Override
    public int getOffsetLength() throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOffsetLength(int length) throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOpen() {
        return null != buffer;
    }

    @Override
    public void close() {
        buffer = null;
    }

    @Override
    public void save() throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void edit() throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getFile()  {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete() throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object readObject() throws JWNLException {
        if (isOpen()) {
            if (buffer != null) {
                try {
		    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer));
		    return in.readObject();
                } catch (IOException e) {
                    throw new JWNLIOException(e);
                } catch (ClassNotFoundException e) {
                    throw new JWNLException(e);
                }
            } else {
                return new HashMap<Object, DictionaryElement>();
            }
        } else {
            throw new JWNLRuntimeException(dictionary.getMessages().resolveMessage("PRINCETON_EXCEPTION_001"));
        }
    }

    @Override
    public void writeObject(Object obj) throws JWNLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void open() throws JWNLException {
        InputStream input = PrincetonResourceObjectDictionaryFile.class.getResourceAsStream(path + "/" + getFilename());
        try {
            try {
                // data.noun is about 16M
                ByteArrayOutputStream output = new ByteArrayOutputStream(16 * 1024 * 1024);
                try {
                    PrincetonResourceDictionaryFile.copyStream(input, output);
                    buffer = output.toByteArray();
		    
                } finally {
                    output.close();
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            throw new JWNLIOException(e);
        }
    }
}
