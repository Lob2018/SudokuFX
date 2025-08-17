/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuUTest {

    @Test
    @DisplayName("Should create Menu with constructor")
    void givenMenuidAndMode_whenCreateMenuWithConstructor_thenMenuIsCreatedCorrectly() {
        Byte menuid = 1;
        byte mode = 2;
        Menu menu = new Menu(menuid, mode);
        assertEquals(menuid, menu.getMenuid());
        assertEquals(mode, menu.getMode());
    }

    @Test
    @DisplayName("Should create Menu with builder")
    void givenMenuidAndMode_whenCreateMenuWithBuilder_thenMenuIsCreatedCorrectly() {
        Byte menuid = 5;
        byte mode = 3;
        Menu menu = Menu.builder().menuid(menuid).mode(mode).build();
        assertEquals(menuid, menu.getMenuid());
        assertEquals(mode, menu.getMode());
    }

    @Test
    @DisplayName("Should create Menu with default constructor")
    void givenNothing_whenCreateMenuWithDefaultConstructor_thenMenuHasDefaultValues() {
        Menu menu = new Menu();
        assertNull(menu.getMenuid());
        assertEquals(0, menu.getMode());
    }

    @Test
    @DisplayName("Should set mode using setter")
    void givenExistingMenu_whenSetModeUsingSetter_thenModeIsUpdated() {
        Menu menu = new Menu();
        byte newMode = 1;
        menu.setMode(newMode);
        assertEquals(newMode, menu.getMode());
    }

    @Test
    @DisplayName("Should handle boundary values for mode")
    void givenBoundaryValues_whenCreateMenu_thenBoundaryValuesAreAccepted() {
        Menu menu1 = Menu.builder().mode((byte) 1).build();
        assertEquals(1, menu1.getMode());
        Menu menu2 = Menu.builder().mode((byte) 3).build();
        assertEquals(3, menu2.getMode());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    @SuppressWarnings({"EqualsWithItself", "ConstantConditions"})
    void givenTwoMenus_whenCompareWithEquals_thenEqualsWorksCorrectly() {
        Menu menu1 = new Menu((byte) 1, (byte) 2);
        Menu menu2 = new Menu((byte) 1, (byte) 2);
        Menu menu3 = new Menu((byte) 1, (byte) 3);
        Menu menu4 = new Menu((byte) 2, (byte) 2);
        assertEquals(menu1, menu2);
        assertNotEquals(menu1, menu3);
        assertNotEquals(menu1, menu4);
        assertEquals(menu1, menu1);
        assertNotEquals(null, menu1);
        boolean equalsObject = menu1.equals(new Object());
        assertFalse(equalsObject);
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void givenMenusWithSameAndDifferentValues_whenGetHashCode_thenHashCodeIsConsistent() {
        Menu menu1 = new Menu((byte) 1, (byte) 2);
        Menu menu2 = new Menu((byte) 1, (byte) 2);
        Menu menu3 = new Menu((byte) 1, (byte) 3);
        assertEquals(menu1.hashCode(), menu2.hashCode());
        assertNotEquals(menu1.hashCode(), menu3.hashCode());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void givenMenuWithValues_whenCallToString_thenCorrectStringRepresentationIsReturned() {
        Menu menu = new Menu((byte) 10, (byte) 2);
        String result = menu.toString();
        assertEquals("Menu{menuid=10, mode=2}", result);
    }

    @Test
    @DisplayName("Should handle null menuid")
    void givenNullMenuid_whenCreateMenu_thenNullMenuidIsHandled() {
        Menu menu = new Menu(null, (byte) 1);
        assertNull(menu.getMenuid());
        assertEquals(1, menu.getMode());
        assertEquals("Menu{menuid=null, mode=1}", menu.toString());
    }

    @Test
    @DisplayName("Builder should be fluent")
    void givenBuilder_whenChainMultipleCalls_thenBuilderIsFluent() {
        Menu menu = Menu.builder().menuid((byte) 5).mode((byte) 2).menuid((byte) 10).build();
        assertEquals((byte) 10, menu.getMenuid());
        assertEquals((byte) 2, menu.getMode());
    }

    @Test
    @DisplayName("Should work with extreme byte values")
    void givenExtremeByteValues_whenCreateMenu_thenExtremeValuesAreHandled() {
        Menu menu1 = new Menu(Byte.MAX_VALUE, (byte) 1);
        Menu menu2 = new Menu(Byte.MIN_VALUE, (byte) 3);
        assertEquals(Byte.MAX_VALUE, menu1.getMenuid());
        assertEquals(1, menu1.getMode());
        assertEquals(Byte.MIN_VALUE, menu2.getMenuid());
        assertEquals(3, menu2.getMode());
    }
}
