package org.example.lombok;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {

    @Test
    public void getterSetter() {
        Member member = new Member();
        member.setName("dongchul");

        assertEquals("dongchul", member.getName());
    }
}
