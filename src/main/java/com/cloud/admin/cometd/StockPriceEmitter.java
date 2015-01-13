package com.cloud.admin.cometd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cloud.admin.model.Notification;

public class StockPriceEmitter implements Runnable
{
	public final static String NOTIFICATION_TASK = "NOTIFICATION_TASK"; 
	public final static String NOTIFICATION_EMAIL = "NOTIFICATION_EMAIL";
	public final static String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
	public final static String NOTIFICATION_COMMENT = "NOTIFICATION_COMMENT";
	public final static String NOTIFICATION_INFO = "NOTIFICATION_INFO";
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final List<String> symbols = new ArrayList<String>();
    private final Map<String, Notification> values = new HashMap<String, Notification>();
    private final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public StockPriceEmitter()
    {
        symbols.addAll(Arrays.asList(NOTIFICATION_TASK, NOTIFICATION_EMAIL, NOTIFICATION_MESSAGE, NOTIFICATION_INFO, NOTIFICATION_COMMENT));
        values.put(NOTIFICATION_TASK, new Notification(NOTIFICATION_TASK));
        values.put(NOTIFICATION_EMAIL, new Notification(NOTIFICATION_EMAIL));
        values.put(NOTIFICATION_MESSAGE, new Notification(NOTIFICATION_MESSAGE));
        values.put(NOTIFICATION_INFO, new Notification(NOTIFICATION_INFO));
        values.put(NOTIFICATION_COMMENT, new Notification(NOTIFICATION_COMMENT));
    }

    public List<Listener> getListeners()
    {
        return listeners;
    }

    public void start()
    {
        run();
    }

    public void stop()
    {
        scheduler.shutdownNow();
    }

    public void run()
    {
        Random random = new Random();
        System.out.println("---server update---");
        List<Update> updates = new ArrayList<Update>();

        // Randomly choose how many stocks to update
        int howMany = random.nextInt(symbols.size()) + 1;
        for (int i = 0; i < howMany; ++i)
        {
            // Randomly choose which one to update
            int which = random.nextInt(symbols.size());
            String symbol = symbols.get(which);
            Notification oldValue = values.get(symbol);

            // Randomly choose how much to update
            boolean sign = random.nextBoolean();
            float howMuch = random.nextFloat();
            Notification newValue = new Notification(symbol, new Date(), "update: " + howMuch, "Content"); 

            // Store the new value
            values.put(symbol, newValue);

            updates.add(new Update(symbol, oldValue, newValue));
        }

        // Notify the listeners
        for (Listener listener : listeners)
        {
            listener.onUpdates(updates);
        }

        // Randomly choose how long for the next update
        // We use a max delay of 1 second to simulate a high rate of updates
        long howLong = random.nextInt(1000);
        scheduler.schedule(this, howLong, TimeUnit.MILLISECONDS);
    }

    public static class Update
    {
        private final String symbol;
        private final Notification oldValue;
        private final Notification newValue;

        public Update(String symbol, Notification oldValue, Notification newValue)
        {
            this.symbol = symbol;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public String getSymbol()
        {
            return symbol;
        }

        public Notification getOldValue()
        {
            return oldValue;
        }

        public Notification getNewValue()
        {
            return newValue;
        }
    }

    public interface Listener extends EventListener
    {
        void onUpdates(List<Update> updates);
    }
}