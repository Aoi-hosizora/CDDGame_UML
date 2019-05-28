package com.oosad.cddgame.Data.Entity;

/**
 * 扑克牌花色
 * 顺序: Diamond < Club < Heart < Spade
 */
public enum CardSuit implements Comparable<CardSuit> {
    /**
     * 方块 ♦
     */
    Diamond,
    /**
     * 梅花 ♣
     */
    Club,
    /**
     * 红桃 ♥
     */
    Heart,
    /**
     * 黑桃 ♠
     */
    Spade
}