package com.zensols.nlp.wnmap.princeton.file;

import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrincetonResourceObjectDictionaryFileTest {
    private static final Log log = LogFactory.getLog(PrincetonResourceObjectDictionaryFileTest.class);

    @BeforeClass
    public static void setup() {
	if (log.isDebugEnabled()) {
	    log.debug("setting up...");
	}
    }

    @AfterClass
    public static void tearDown() {
	if (log.isDebugEnabled()) {
	    log.debug("tearing down...");
	}
    }

    @Test
    public void testHelloWorld() throws Exception {
	InputStream in = getClass().getResourceAsStream("/net/sf/extjwnl/data/wordnet/wn31/map/res_properties.xml");
	Assert.assertNotNull(in);
	Dictionary dict = Dictionary.getInstance(in);
	Assert.assertNotNull(dict);
	IndexWordSet wordSet = dict.lookupAllIndexWords("cat");
	Assert.assertNotNull(wordSet);
	IndexWord indexWord = wordSet.getIndexWord(POS.NOUN);
	Assert.assertNotNull(indexWord);
	log.info("cat noun index word: " + indexWord);
    }
}
