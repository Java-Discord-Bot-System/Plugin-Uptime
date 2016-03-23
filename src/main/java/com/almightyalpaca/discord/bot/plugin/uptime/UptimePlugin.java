package com.almightyalpaca.discord.bot.plugin.uptime;

import java.lang.management.ManagementFactory;

import com.almightyalpaca.discord.bot.system.command.AbstractCommand;
import com.almightyalpaca.discord.bot.system.command.annotation.Command;
import com.almightyalpaca.discord.bot.system.events.CommandEvent;
import com.almightyalpaca.discord.bot.system.exception.PluginLoadingException;
import com.almightyalpaca.discord.bot.system.exception.PluginUnloadingException;
import com.almightyalpaca.discord.bot.system.plugins.Plugin;
import com.almightyalpaca.discord.bot.system.plugins.PluginInfo;
import com.almightyalpaca.discord.bot.system.util.StringUtils;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.MessageBuilder.Formatting;

public class UptimePlugin extends Plugin {

	class UptimeCommand extends AbstractCommand {

		public UptimeCommand() {
			super("uptime", "How long am i running?", "");
		}

		@Command(dm = true, guild = true)
		private void onCommand(final CommandEvent event) {

			final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

			final long years = duration / 31104000000L;
			final long months = duration / 2592000000L % 12;
			final long days = duration / 86400000L % 30;
			final long hours = duration / 3600000L % 24;
			final long minutes = duration / 60000L % 60;
			final long seconds = duration / 1000L % 60;
			final long milliseconds = duration % 1000;

			String uptime = (years == 0 ? "" : years + " Years, ") + (months == 0 ? "" : months + " Months, ") + (days == 0 ? "" : days + " Days, ") + (hours == 0 ? "" : hours + " Hours, ")
					+ (minutes == 0 ? "" : minutes + " Minutes, ") + (seconds == 0 ? "" : seconds + " Seconds, ") + (milliseconds == 0 ? "" : milliseconds + " Milliseconds, ");

			uptime = StringUtils.replaceLast(uptime, ", ", "");
			uptime = StringUtils.replaceLast(uptime, ",", " and");

			final MessageBuilder builder = new MessageBuilder();

			builder.appendString("I'm online for ");
			builder.appendString(uptime, Formatting.BOLD);
			builder.appendString(".");

			event.sendMessage(builder.build());

		}

	}

	private static final PluginInfo INFO = new PluginInfo("com.almightyalpaca.discord.bot.plugin.uptime", "1.0.0", "Almighty Alpaca", "Uptime Plugin", "How long do i run?");

	public UptimePlugin() {
		super(UptimePlugin.INFO);
	}

	@Override
	public void load() throws PluginLoadingException {
		this.registerCommand(new UptimeCommand());
	}

	@Override
	public void unload() throws PluginUnloadingException {}
}
